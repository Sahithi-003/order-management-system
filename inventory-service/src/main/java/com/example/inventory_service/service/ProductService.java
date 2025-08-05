package com.example.inventory_service.service;

import com.example.inventory_service.entity.Product;
import com.example.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public Product addProduct(Product product){
        return productRepository.save(product);
    }
     public List<Product> getAllProducts(){
        return productRepository.findAll();
     }
     public boolean reserveProduct(Long id,int quantity){
         Product product= productRepository.findById(id)
                 .orElseThrow(()->new RuntimeException("Product Not found"));
            if(product.getQuantity()>quantity){
                product.setQuantity(product.getQuantity()-quantity);
                productRepository.save(product);
                return true;
            }
            return false;
     }
}
