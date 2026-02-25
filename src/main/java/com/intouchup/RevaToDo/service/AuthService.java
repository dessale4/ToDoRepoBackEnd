package com.intouchup.RevaToDo.service;

import com.intouchup.RevaToDo.auth.AuthenticationRequest;
import com.intouchup.RevaToDo.auth.AuthenticationResponse;
import com.intouchup.RevaToDo.entity.Role;
import com.intouchup.RevaToDo.entity.User;
import com.intouchup.RevaToDo.repository.RoleRepository;
import com.intouchup.RevaToDo.repository.UserRepository;
import com.intouchup.RevaToDo.auth.RegistrationRequest;
import com.intouchup.RevaToDo.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Service

@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String register(RegistrationRequest registrationRequest) {
        Role userRole = roleRepository.findByName("User").orElse(Role.builder()
                .name("User")
                .build());
        User registeringUser = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(List.of(userRole))
                .build();
         userRepository.save(registeringUser);
        return  "Your Registration is Successful";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) throws ParseException {
        User storedUser = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("No account with email " + request.getEmail()));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        claims.put("fullName", storedUser.fullName());
        var jwtAccessToken = jwtService.generateToken(claims, storedUser, false);
        return AuthenticationResponse.builder()
                .jwtToken(jwtAccessToken)
                .loggedUser(storedUser)
                .build();
    }
}
