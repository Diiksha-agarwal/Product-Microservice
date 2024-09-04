package com.example.product.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = Product.COLLECTION_NAME)
@Data
public class Product {
    public static final String COLLECTION_NAME = "Product";
    @Id
    private String pId;
    private String pName;
    private String description;
    private String image;
    private List<String> usp;
    private List<String> attribute;
    private List<Seller> seller;
}
