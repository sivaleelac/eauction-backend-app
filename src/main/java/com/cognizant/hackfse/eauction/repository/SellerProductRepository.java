package com.cognizant.hackfse.eauction.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.hackfse.eauction.entity.ProductEntity;

@Repository
public interface SellerProductRepository extends MongoRepository<ProductEntity, String> {
	@Query("{'person.emailAddress' : ?0 }")
	List<ProductEntity> findAllByPersonEmailAddress(String emailAddress);
}
