package com.cognizant.hackfse.eauction.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.hackfse.eauction.entity.BuyerProductBidEntity;
import com.cognizant.hackfse.eauction.entity.BuyerProductBidEntity.Bid;
import com.cognizant.hackfse.eauction.exception.InternalServerException;
import com.cognizant.hackfse.eauction.model.Bidder;
import com.cognizant.hackfse.eauction.repository.BuyerProductBidRepository;

@Service
public class BuyerBidderService {
	private static final Logger log = LoggerFactory.getLogger(BuyerBidderService.class);

	@Autowired
	private BuyerProductBidRepository buyerProductBidRepository;

	public void createOrUpdateBidderAndProductDetails(Bidder bidder) {
		try {
			log.info("------ Inside BuyerBidderService > createOrUpdateBidderAndProductDetails ----");
			Optional<BuyerProductBidEntity> buyerProductBidEntityOptional = buyerProductBidRepository
					.findByProductId(bidder.getProduct().getId());
			BuyerProductBidEntity buyerProductBidEntity = new BuyerProductBidEntity();
			List<Bid> bids = new ArrayList<>();
			if (buyerProductBidEntityOptional.isPresent()) {
				buyerProductBidEntity = buyerProductBidEntityOptional.get();
				bids = buyerProductBidEntity.getBids();
			}else {
				buyerProductBidEntity.setProduct(bidder.getProduct());
			}
			buyerProductBidEntity.setBids(buildBids(bidder, bids));
			buyerProductBidRepository.save(buyerProductBidEntity);
		} catch (Exception exp) {
			throw new InternalServerException("Unable to fetch product with bid details");
		}
	}

	private List<Bid> buildBids(Bidder bidder, List<Bid> bids) {
		List<Bid> newBids = bids;
		Bid bid = new Bid();
		bid.setBidAmount(bidder.getBidAmount());
		bid.setPerson(bidder.getPerson());
		newBids.add(bid);
		return newBids;
	}
}
