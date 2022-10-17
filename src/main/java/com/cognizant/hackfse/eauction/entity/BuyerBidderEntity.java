package com.cognizant.hackfse.eauction.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.hackfse.eauction.model.Person;
import com.cognizant.hackfse.eauction.model.Product;

import lombok.Data;

@Document(collection = "bidder")
@Data
public class BuyerBidderEntity {
	@Id
	private String id;
	private Person person;
	private Product product;
	private String bidAmount;	
}
