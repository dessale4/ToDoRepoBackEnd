package com.intouchup.RevaToDo.auth;

import com.intouchup.RevaToDo.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String jwtToken;
    private User loggedUser;
}
