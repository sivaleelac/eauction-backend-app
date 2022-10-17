package com.cognizant.hackfse.eauction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.hackfse.eauction.model.ProductBidder;
import com.cognizant.hackfse.eauction.service.SellerProductService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://react-eauction-aws-hosting.s3.amazonaws.com", "https://react-eauction-aws-hosting.s3.amazonaws.com"})
public class SellerBidController {

	@Autowired
	private SellerProductService sellerProductService;
	
	@GetMapping("/api/v1/seller/show-bids/{productId}")
	public ResponseEntity<ProductBidder> getProduct(@PathVariable("productId") String productId) {
		ProductBidder productBidder = sellerProductService.getProductWithBidDetails(productId);
		return new ResponseEntity<>(productBidder, HttpStatus.OK);
	}
}
