package com.cognizant.hackfse.eauction.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.hackfse.eauction.model.Person;
import com.cognizant.hackfse.eauction.model.Product;

import lombok.Data;

@Document(collection = "productbiddata")
@Data
public class BuyerProductBidEntity {
	@Id
	private String id;
	private Product product;
	private List<Bid> bids = new ArrayList<>();
	
	@Data
	public static class Bid{
		private Person person;
		private String bidAmount;
	}
}
