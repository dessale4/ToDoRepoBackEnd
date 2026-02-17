package com.intouchup.RevaToDo.service;

import com.intouchup.RevaToDo.entity.User;
import com.intouchup.RevaToDo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    public User getUserInfo(Principal principal) {
        String userEmail = principal.getName();
        return userRepository.findByEmail(userEmail).orElseThrow(()->new AccessDeniedException("Not allowed to so!!"));
    }
}
