package com.intouchup.RevaToDo.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {
    @NotBlank(message = "Email is a required field")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is a required field")
    private String password;
}
