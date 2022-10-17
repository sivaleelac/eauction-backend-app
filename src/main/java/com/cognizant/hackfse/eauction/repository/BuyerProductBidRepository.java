package com.cognizant.hackfse.eauction.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cognizant.hackfse.eauction.entity.BuyerProductBidEntity;

public interface BuyerProductBidRepository extends MongoRepository<BuyerProductBidEntity, String> {
	
	@Query("{ 'product._id' : ObjectId(?0)}")
	Optional<BuyerProductBidEntity> findByProductId(String productId);
}
