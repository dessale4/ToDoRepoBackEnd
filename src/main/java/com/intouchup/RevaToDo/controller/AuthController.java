package com.intouchup.RevaToDo.controller;

import com.intouchup.RevaToDo.entity.User;
import com.intouchup.RevaToDo.reqDTO.AuthRequest;
import com.intouchup.RevaToDo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
//    @Autowired
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody AuthRequest authRequest){
        User newUser = authService.register(authRequest);
        return ResponseEntity.ok(newUser);
    }
}
