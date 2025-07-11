package com.alejandro.ecommerceapi.dto;

import com.alejandro.ecommerceapi.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role; // CLIENT o ADMIN
}
