package com.example.product.service;

import com.example.product.dto.ProductDTO;
import com.example.product.dto.ProductDescDTO;
import com.example.product.entity.Seller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public boolean createProduct(ProductDTO productDTO);

    public List<ProductDescDTO> getAll(int pageNumber, int pageSize);

    public ProductDTO getById(String pid);
    public boolean addSeller(String pId, Seller seller);
}
