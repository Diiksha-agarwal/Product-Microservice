package com.example.product.controller;

import com.example.product.common.APIResponse;
import com.example.product.dto.ProductDTO;
import com.example.product.dto.ProductDescDTO;
import com.example.product.entity.Seller;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public ProductService productService;
    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<APIResponse<String>> createProduct(@RequestBody ProductDTO productDTO){
        if(productService.createProduct(productDTO)){
            return new ResponseEntity<>(new APIResponse<>("Product created successfully"),HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIResponse<>(false,"something went wrong"),HttpStatus.BAD_REQUEST);
    }
    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<ProductDescDTO>>> findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize){
        List<ProductDescDTO> productDescDTOList = productService.getAll(pageNumber,pageSize);
        if(productDescDTOList == null){
            return new ResponseEntity<>(new APIResponse<>(false,"something went wrong"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new APIResponse<>(productDescDTOList),HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping("/{pid}")
    public ResponseEntity<APIResponse<ProductDTO>> findById(@PathVariable("pid") String pid){
        ProductDTO productDTO = productService.getById(pid);
        if (productDTO == null){
            return new ResponseEntity<>(new APIResponse<>(false,"product not found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new APIResponse<>(productDTO),HttpStatus.OK);
    }
    @CrossOrigin
    @PostMapping("/addSeller")
    public ResponseEntity<APIResponse<String>> addSeller(String pId, Seller seller){
        if(productService.addSeller(pId,seller)){
            return new ResponseEntity<>(new APIResponse<>("Listed seller"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIResponse<>(false,"product not found"),HttpStatus.BAD_REQUEST);
    }
}
