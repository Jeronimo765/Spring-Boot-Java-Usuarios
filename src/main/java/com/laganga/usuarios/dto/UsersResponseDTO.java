package com.laganga.usuarios.dto;

import lombok.Data;

@Data
public class UsersResponseDTO {
    private Long id;
    private String username;
    private String email;
}