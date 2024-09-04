package com.example.product.service.serviceImplementation;

import com.example.product.common.RandomIdGenerator;
import com.example.product.dto.ProductDTO;
import com.example.product.dto.ProductDescDTO;
import com.example.product.entity.Product;
import com.example.product.entity.Seller;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {
    @Autowired
    public ProductRepository productRepository;

    @Override
    public boolean createProduct(ProductDTO productDTO){
        if(productDTO == null){
            return false;
        }
        String name = productDTO.getPName();
        Optional<Product> existingProduct = productRepository.findByPNameIgnoreCase(name);
        if (existingProduct.isPresent()){
            return true;
        }
        Product product = new Product();
        BeanUtils.copyProperties(productDTO,product);
        product.setPId(RandomIdGenerator.generateRandomId());
        productRepository.insert(product);
        return true;
    }
    @Override
    public List<ProductDescDTO> getAll(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> productList = page.getContent();
        List<ProductDescDTO> productDTOList = new ArrayList<>();
        for(Product product:productList){
            List<Seller> sellerList = product.getSeller();
            double minPrice = sellerList.get(0).getPrice();
            String minSId = sellerList.get(0).getSId();
            for(Seller seller:sellerList){
                double price = seller.getPrice();
                if(price < minPrice){
                    minPrice = price;
                    minSId = seller.getSId();
                }
            }
            ProductDescDTO productDTO = new ProductDescDTO();
            BeanUtils.copyProperties(product,productDTO);
            productDTO.setMinPrice(minPrice);
            productDTO.setMinSId(minSId);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }
    @Override
    public ProductDTO getById(String pid){
        Optional<Product> product = productRepository.findById(pid);
        if(product.isPresent()){
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(product.get(),productDTO);
            return productDTO;
        }
        else
            return null;
    }
    @Override
    public boolean addSeller(String pId, Seller seller){
        ProductDTO productDTO = getById(pId);
        if(productDTO == null){
            return false;
        }
        List<Seller> sellerList = productDTO.getSeller();
        if(sellerList==null){
            sellerList = new ArrayList<>();
        }
        boolean sellerExists = false;
        for(Seller seller1 :sellerList){
            if(Objects.equals(seller1.getSId(), seller.getSId())){
                seller1.setStock(seller.getStock());
                seller1.setPrice(seller.getPrice());
                seller1.setSName(seller.getSName());
                sellerExists = true;
                break;
            }
        }
        if(!sellerExists){
            sellerList.add(seller);
        }
        productDTO.setSeller(sellerList);
        Product product = new Product();
        BeanUtils.copyProperties(productDTO,product);
        productRepository.save(product);
        return true;
    }
    @Override
    public boolean updateStock(String pId, String sId, int quantity){
        Optional<Product> product = productRepository.findById(pId);
        if(product.isPresent()){
            List<Seller> sellerList = product.get().getSeller();
            if (sellerList == null) {
                return false;
            }
            for(Seller seller:sellerList){
                if(Objects.equals(seller.getSId(), sId)){
                    seller.setStock(seller.getStock()-quantity);
                    break;
                }
            }
            productRepository.save(product.get());
            return true;
        }
        return false;
    }
}
