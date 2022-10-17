package com.cognizant.hackfse.eauction.model;

import lombok.Data;

@Data
public class Bidder {
	private String id;
	private Person person;
	private Product product;
	private String bidAmount;
}
