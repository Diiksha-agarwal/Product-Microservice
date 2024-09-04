package com.example.product.dto;

import lombok.Data;

@Data
public class ProductDescDTO {
    private String pId;
    private String pName;
    private String description;
    private String image;
    private double minPrice;
}
