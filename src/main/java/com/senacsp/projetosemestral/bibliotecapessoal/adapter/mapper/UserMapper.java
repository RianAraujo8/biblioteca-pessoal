package com.senacsp.projetosemestral.bibliotecapessoal.adapter.mapper;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class UserMapper {

    public User toUser(UserRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("UserRequestDto não pode ser nulo");
        }

        return User.builder()
                .cpf(dto.getCpf())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .cep(dto.getCep())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }

    public UserResponseDto toResponseDto(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User não pode ser nulo");
        }

        return UserResponseDto.builder()
                .id(user.getId())
                .cpf(user.getCpf())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .cep(user.getCep())
                .username(user.getUsername())
                .build();
    }

    public List<User> toUserList(List<UserRequestDto> dtos) {
        if (dtos == null) {
            return List.of();
        }

        return dtos.stream()
                .filter(Objects::nonNull)
                .map(this::toUser)
                .toList();
    }

    public List<UserResponseDto> toResponseDtoList(List<User> users) {
        if (users == null) {
            return List.of();
        }

        return users.stream()
                .filter(Objects::nonNull)
                .map(this::toResponseDto)
                .toList();
    }

    public void updateUser(User user, UserRequestDto dto) {
        if (user == null || dto == null) {
            throw new IllegalArgumentException("User ou UserRequestDto não pode ser nulo");
        }

        user.setCpf(dto.getCpf());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setCep(dto.getCep());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
    }
}
