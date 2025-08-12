package com.example.order_service;

import com.example.order_service.entity.Order;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testPlaceOrder(){
        when(restTemplate.getForEntity(
                anyString(),eq(String.class)
        )).thenReturn(ResponseEntity.ok("User exists"));
        when(restTemplate.postForEntity(anyString(),isNull(),eq(String.class))).thenReturn(ResponseEntity.ok("Reserved"));
        Order dummyOrder = new Order(null, 1L, 1L, 2, LocalDateTime.now());
        when(orderRepository.save(any(Order.class))).thenReturn(dummyOrder);
        Order order = orderService.placeOrder(1L,1L,2);
        assertEquals(1L,order.getUserId());
        assertNotNull(order);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testGetOrder(){
        Order dummyOrder= new Order(1L,1L,1L,3,LocalDateTime.now());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(dummyOrder));
        Optional<Order> foundOrder= Optional.ofNullable(orderService.getOrder(1L));
        assertEquals(dummyOrder.getUserId(),foundOrder.get().getUserId());
        verify(orderRepository,times(1)).findById(1L);

    }
}
