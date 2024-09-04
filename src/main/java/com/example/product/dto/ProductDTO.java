package com.example.product.dto;

import com.example.product.entity.Seller;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private String pId;
    private String pName;
    private String description;
    private String image;
    private List<String> usp;
    private List<String> attribute;
    private List<Seller> seller;
}
