package com.laganga.usuarios.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laganga.usuarios.dto.MessageResponseDTO;
import com.laganga.usuarios.dto.UsersRequestDTO;
import com.laganga.usuarios.dto.UsersResponseDTO;
import com.laganga.usuarios.services.UsersService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/users")
@RestController
// Context path
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createUser(@RequestBody UsersRequestDTO request) {
        MessageResponseDTO response = new MessageResponseDTO();
        try {
            response = usersService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setMessage("Hubo un error al crear el usuario");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UsersResponseDTO dto = usersService.getUserById(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserByUsername(@RequestParam(value = "username", required = false) String username) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body("Parametro username es requerido");
        }
        try {
            UsersResponseDTO dto = usersService.getUserByUsername(username);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateUser(@PathVariable Long id, @RequestBody UsersRequestDTO request) {
        try {
            MessageResponseDTO response = usersService.updateUser(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            MessageResponseDTO errorResponse = new MessageResponseDTO();
            errorResponse.setMessage("No se pudo actualizar el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteUser(@PathVariable Long id) {
        try {
            MessageResponseDTO response = usersService.deleteUser(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            MessageResponseDTO errorResponse = new MessageResponseDTO();
            errorResponse.setMessage("No se pudo eliminar el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
