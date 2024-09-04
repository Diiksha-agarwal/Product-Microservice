package com.example.product.repository;

import com.example.product.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    @Query("{ 'pName': { $regex: ?0, $options: 'i' } }")
    Optional<Product> findByPNameIgnoreCase(String name);
}
