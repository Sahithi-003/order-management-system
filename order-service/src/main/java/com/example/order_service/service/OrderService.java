package com.example.order_service.service;

import com.example.order_service.entity.Order;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public Order placeOrder(Long userId,Long productId,int quantity){
        ResponseEntity<String> userResp=restTemplate.getForEntity("http://localhost:8081/users/"+userId,String.class);
        if(!userResp.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException("User npt found");
        }
        ResponseEntity<String> productResponse = restTemplate.postForEntity("http://localhost:8082/inventory/reserve?id="+productId+"&quantity="+quantity,null,String.class);
        if(!productResponse.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException("Product reservation failed");
        }
        Order order = new Order();
        order.setProductId(productId);
        order.setUserId(userId);
        order.setQuantity(quantity);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public Order getOrder(Long id){
        return orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));
    }
}
