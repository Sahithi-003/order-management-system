package com.example.inventory_service;

import com.example.inventory_service.entity.Product;
import com.example.inventory_service.repository.ProductRepository;
import com.example.inventory_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testAddProduct(){
        Product product = new Product(1L,"Laptop",2);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product foundProduct = productService.addProduct(product);
        assertEquals(product.getQuantity(),foundProduct.getQuantity());
    }

    @Test
    void testGetAllProducts(){
        Product product1 = new Product(1L,"Laptop",2);
        Product product2 = new Product(2L,"Mobile",2);

        List<Product> list = Arrays.asList(product1,product2);
        when(productRepository.findAll()).thenReturn(list);
        List<Product> foundList=productService.getAllProducts();
        assertEquals(foundList.size(),list.size());
    }

    @Test
    void testReserveProduct(){
        Product product1 = new Product(1L,"Laptop",10);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        boolean success = productService.reserveProduct(1L,2);
        assertTrue(success);
        assertEquals(8,product1.getQuantity());

    }

}
