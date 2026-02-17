package com.intouchup.RevaToDo.controller;

import com.intouchup.RevaToDo.auth.AuthenticationRequest;
import com.intouchup.RevaToDo.auth.AuthenticationResponse;
import com.intouchup.RevaToDo.auth.RegistrationRequest;
import com.intouchup.RevaToDo.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.text.ParseException;
import java.util.Base64;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class AuthController {
    //    @Autowired
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        String registrationStatus = authService.register(registrationRequest);
        return ResponseEntity.ok(registrationStatus);
    }

    @GetMapping("/getSecretKey")
    public ResponseEntity<?> getSecreteKey() {
        // 1. Generate the SecretKey (as you have done)
        SecretKey key = Jwts.SIG.HS256.key().build();

// 2. Get the raw bytes
        byte[] rawData = key.getEncoded();

// 3. Encode the bytes to a Base64 string for storage/display
// You can use the standard Java Base64 encoder or the JJWT specific one
        String encodedKey = Base64.getEncoder().encodeToString(rawData); // Standard Java
// or using JJWT's Encoders for consistency
        String encodedKeyJJWT = Encoders.BASE64.encode(rawData);

// Print the key (for development/debugging - do not do this in production)
        System.out.println("Secret key (Base64 encoded): " + encodedKey);
        return ResponseEntity.ok(encodedKeyJJWT);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
            , HttpServletResponse response
    ) throws MessagingException, ParseException {
        return ResponseEntity.ok(authService.authenticate(request, response));
    }
}
