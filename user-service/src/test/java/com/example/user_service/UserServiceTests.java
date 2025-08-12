package com.example.user_service;

import com.example.user_service.entity.User;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveUser(){
        User dummyUser = new User(1L,"test name","test@gmail.com");
        when(userRepository.save(any(User.class))).thenReturn(dummyUser);
        User foundUser = userService.save(dummyUser);
        assertEquals("test name",foundUser.getName());
    }

    @Test
    void testGetUserById(){
        User dummyUser = new User(1L,"test name","test@gmail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(dummyUser));

        User foundUser = userService.getUser(1L);
        assertEquals(dummyUser.getName(),foundUser.getName());
    }
}
