package com.intouchup.RevaToDo.controller;

import com.intouchup.RevaToDo.entity.User;
import com.intouchup.RevaToDo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/personal")
public class UserController {
    private final UserService userService;
    @GetMapping("/userInfo")
    public ResponseEntity<User> getUserInfo(Principal principal){
        User registeredUser =  userService.getUserInfo(principal);
        return ResponseEntity.ok(registeredUser);
    }
}
