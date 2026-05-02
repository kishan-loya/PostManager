package com.example.tutorial.dto;

import com.example.tutorial.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
}
