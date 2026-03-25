package com.laganga.usuarios.services;

import org.springframework.stereotype.Service;

import com.laganga.usuarios.dto.MessageResponseDTO;
import com.laganga.usuarios.dto.UsersRequestDTO;
import com.laganga.usuarios.dto.UsersResponseDTO;
import com.laganga.usuarios.entity.Users;
import com.laganga.usuarios.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service // Crea un bean (instancia)
@RequiredArgsConstructor
public class UsersService {
    // Inyección de dependencias
    private final UsersRepository usersRepository; // Inyectamos el repository

    /**
     * Este metodo es para crear un usuario
     * 
     * @param request datos del usuario a crear
     * @return MessageResponseDTO objeto de respuesta que contiene un mensaje
     */
    public MessageResponseDTO createUser(UsersRequestDTO request) {
        if (request == null || request.getUsername() == null || request.getEmail() == null
                || request.getUsername().isBlank() || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Los campos username y email son obligatorios");
        }

        MessageResponseDTO response = new MessageResponseDTO();

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        usersRepository.save(user);

        response.setMessage("Usuario creado correctamente");
        return response;
    }

    public UsersResponseDTO getUserById(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));
        UsersResponseDTO dto = new UsersResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public UsersResponseDTO getUserByUsername(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con username: " + username));
        UsersResponseDTO dto = new UsersResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public MessageResponseDTO updateUser(Long id, UsersRequestDTO request) {
        if (request == null || request.getUsername() == null || request.getEmail() == null
                || request.getUsername().isBlank() || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Los campos username y email son obligatorios");
        }

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        usersRepository.save(user);

        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Usuario actualizado correctamente");
        return response;
    }

    public MessageResponseDTO deleteUser(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));

        usersRepository.delete(user);

        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Usuario eliminado correctamente");
        return response;
    }
}
