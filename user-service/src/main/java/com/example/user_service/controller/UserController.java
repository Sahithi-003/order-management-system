package com.example.user_service.controller;

import com.example.user_service.entity.User;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
 private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
 @PostMapping
 public ResponseEntity<User> create(@RequestBody User user){
     return ResponseEntity.ok(userService.save(user));
 }

 @GetMapping("/{id}")
 public ResponseEntity<User> get(@PathVariable Long id){
     return ResponseEntity.ok(userService.getUser(id));
 }
}
