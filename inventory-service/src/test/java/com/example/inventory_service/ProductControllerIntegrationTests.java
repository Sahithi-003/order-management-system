package com.example.inventory_service;

import com.example.inventory_service.controller.ProductController;
import com.example.inventory_service.entity.Product;
import com.example.inventory_service.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    @Test
    void testAddProduct() throws Exception {
        Product product = new Product(1L, "Laptop", 2);
        when(productService.addProduct(product)).thenReturn(product);

        mockMvc.perform(post("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void testGetProduct() throws Exception {
        Product product1 = new Product(1L, "Laptop", 2);
        Product product2 = new Product(2L, "Mobile", 22);
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testReserveProduct() throws Exception{
        Product product = new Product(1L,"Laptop",20);
        when(productService.reserveProduct(1L, 2)).thenReturn(true);
        mockMvc.perform(post("/inventory/reserve")
                        .param("id","1")
                        .param("quantity","2"))
                .andExpect(status().isOk());
    }
}
