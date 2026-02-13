package com.intouchup.RevaToDo.service;

import com.intouchup.RevaToDo.entity.Item;
import com.intouchup.RevaToDo.entity.User;
import com.intouchup.RevaToDo.repository.UserRepository;
import com.intouchup.RevaToDo.reqDTO.AuthRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    public User register(AuthRequest authRequest) {
        Item item = Item.builder()
                .name("Test")
                .build();
//        User registeringUser = User.builder().build();
        User registeringUser = User.builder()
                .firstName(authRequest.getFirstName())
                .lastName(authRequest.getLastName())
                .email(authRequest.getEmail())
                .password(authRequest.getPassword())
                .toDoes(List.of(item))
                .build();
        return userRepository.save(registeringUser);
    }
}
