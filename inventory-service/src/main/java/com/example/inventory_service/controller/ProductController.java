package com.example.inventory_service.controller;

import com.example.inventory_service.entity.Product;
import com.example.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> add(@RequestBody Product product){
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @GetMapping
    public ResponseEntity<List<Product>> list(){
       return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(@RequestParam Long id,@RequestParam int quantity){
        boolean success= productService.reserveProduct(id,quantity);
        if (success){
            return ResponseEntity.ok("Reserved");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Insufficient quantity");
        }
    }
}
