package com.cognizant.hackfse.eauction.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.hackfse.eauction.model.Person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
	@Id private String id;
	private String productName;
	private String shortDescription;
	private String detailedDescription;
	private String category;
	private String startingPrice;
	private String bidEndDate;
	private Person person;
}
