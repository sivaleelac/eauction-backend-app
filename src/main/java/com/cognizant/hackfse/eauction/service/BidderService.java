package com.cognizant.hackfse.eauction.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.hackfse.eauction.entity.BuyerBidderEntity;
import com.cognizant.hackfse.eauction.exception.BadRequestException;
import com.cognizant.hackfse.eauction.exception.InternalServerException;
import com.cognizant.hackfse.eauction.exception.NotFoundException;
import com.cognizant.hackfse.eauction.model.BidderResource;
import com.cognizant.hackfse.eauction.model.Person;
import com.cognizant.hackfse.eauction.model.Product;
import com.cognizant.hackfse.eauction.producer.QueueProducer;
import com.cognizant.hackfse.eauction.repository.BidderRepository;

@Service
public class BidderService {
	private static final Logger log = LoggerFactory.getLogger(BidderService.class);

	@Autowired
	private BidderRepository bidderRepository;
	@Autowired
	private SellerProductService productClient;
	@Autowired
	private PersonService personClient;
	@Autowired
	private QueueProducer queueProducer;

	public BidderResource getBid(String emailAddress, String productId) {
		log.info("----------------- Inside BidderService > getBid start -------------");
		validateAndGetPerson(emailAddress);
		validateAndGetProduct(productId);
		validateProductAndPerson(productId, emailAddress);
		return new BidderResource();
	}
	
	public BidderResource placeBid(BidderResource bidder, String emailAddress) {
		log.info("----------------- Inside BidderService > placeBid start -------------");
		Person person = validateAndGetPerson(emailAddress);
		Product product = validateAndGetProduct(bidder.getProduct().getId());
		log.info("----------------- Inside BidderService > placeBid product - {}", product);
		validateBidEndDate(product);
		validateProductAndPerson(bidder.getProduct().getId(), person.getEmailAddress());
		try {
			ModelMapper mapper = new ModelMapper();
			BuyerBidderEntity savedEntity = bidderRepository.save(buildBidderEntity(mapper, product, person, bidder));
			BidderResource response = mapper.map(savedEntity, BidderResource.class);
			sendBidDetailsToQueue(savedEntity);
			log.info("----------------- Inside BidderService > placeBid end -------------");
			return response;
		} catch (Exception exp) {
			throw new InternalServerException("Internal server error" + exp);
		}
	}

	private BuyerBidderEntity buildBidderEntity(ModelMapper mapper, Product product, Person person,
			BidderResource bidder) {
		BuyerBidderEntity bidderEntity = mapper.map(bidder, BuyerBidderEntity.class);
		bidderEntity.setPerson(person);
		bidderEntity.setProduct(product);
		return bidderEntity;
	}

	private Product validateAndGetProduct(String productId) {
		try {
			return productClient.findProductById(productId);
		} catch (Exception exp) {
			throw new NotFoundException("Product not found");
		}
	}

	private void validateBidEndDate(Product product) {
		if (StringUtils.isNotBlank(product.getBidEndDate())
				&& LocalDate.now().isAfter(getFormattedBidDate(product.getBidEndDate()))) {
			throw new BadRequestException("Bid is placed after the bid end date");
		}
	}

	private Person validateAndGetPerson(String emailAddress) {
		try {
			return personClient.getPersonByEmailAddress(emailAddress);
		} catch (Exception exp) {
			throw new NotFoundException("Person not found");
		}
	}

	private void validateProductAndPerson(String productId, String emailAddress) {
		BuyerBidderEntity entity = null;
		try {
			entity = bidderRepository.findByProductIdAndPersonEmailAddress(productId, emailAddress);
		} catch (Exception exp) {
			throw new NotFoundException("Person and Product not found" + exp);
		}
		if (entity != null) {
			throw new BadRequestException("Person with the same product already exists");
		}
	}

	private LocalDate getFormattedBidDate(String bidEnddate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			return LocalDate.parse(bidEnddate, formatter);
		} catch (DateTimeParseException e) {
			throw new BadRequestException("error.client.bidEndDate.invalid");
		}
	}

	public void updateBidAmount(String productId, String emailAddress, String newBidAmount) {
		Product product = validateAndGetProduct(productId);
		validateBidEndDate(product);
		try {
			BuyerBidderEntity entity = bidderRepository.findByProductIdAndPersonEmailAddress(productId, emailAddress);
			entity.setBidAmount(newBidAmount);
			bidderRepository.save(entity);
		} catch (Exception exp) {
			throw new InternalServerException("Internal server error");
		}
	}

	private void sendBidDetailsToQueue(BuyerBidderEntity buyerBidderEntity) {
		log.info("----------------- Inside BidderService > sendBidDetailsToQueue -------------");
		try {
			queueProducer.produce(buyerBidderEntity);
		} catch (Exception e) {

		}
	}
}
