package com.example.order_service;

import com.example.order_service.entity.Order;
import com.example.order_service.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderTestControllerIntegrationTest {

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void testOrderController() throws Exception {
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(ResponseEntity.ok("User exists"));
        when(restTemplate.postForEntity(anyString(), isNull(), eq(String.class)))
                .thenReturn(ResponseEntity.ok("Reserved"));

        MvcResult result = mockMvc.perform(post("/orders")
                        .param("userId", "1")
                        .param("productId", "1")
                        .param("quantity", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.quantity").value(2))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response: " + responseBody);
        assertNotNull(responseBody);
        // You can parse and assert more fields from the JSON if needed
    }

    @Test
    void testGetOrder() throws Exception{
        Order order = new Order(null, 1L, 1L, 2, LocalDateTime.now());
        orderRepository.save(order);
        mockMvc.perform(get("/orders/"+1L))
                .andExpect(status().isOk());
    }
}
