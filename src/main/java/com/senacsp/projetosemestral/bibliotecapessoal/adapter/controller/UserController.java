package com.senacsp.projetosemestral.bibliotecapessoal.adapter.controller;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.aplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/biblioteca-pessoal/v1/usuarios")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> register(
            @RequestBody @Valid UserRequestDto userDto
    ) {
        UserResponseDto createdUser = userService.register(userDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> list() {
        List<UserResponseDto> members = userService.list();

        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(
            @PathVariable String id
    ) {
        UserResponseDto user = userService.getById(id);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable String id,
            @RequestBody @Valid UserRequestDto userDto
    ) {
        UserResponseDto updatedUser =
                userService.updateById(id, userDto);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id
    ) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
