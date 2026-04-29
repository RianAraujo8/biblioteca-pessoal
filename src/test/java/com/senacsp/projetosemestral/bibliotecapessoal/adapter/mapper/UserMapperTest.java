package com.senacsp.projetosemestral.bibliotecapessoal.adapter.mapper;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;
    private UserRequestDto requestDto;
    private User user;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();

        requestDto = UserRequestDto.builder()
                .cpf("12345678900")
                .firstName("Eduardo")
                .lastName("Lublanski")
                .cep("01001000")
                .username("edulub")
                .password("123456")
                .build();

        user = User.builder()
                .id("abc123")
                .cpf("12345678900")
                .firstName("Eduardo")
                .lastName("Lublanski")
                .cep("01001000")
                .username("edulub")
                .password("123456")
                .build();
    }

    @Test
    void shouldConvertRequestDtoToUser() {
        User convertedUser = userMapper.toUser(requestDto);

        assertNotNull(convertedUser);
        assertEquals("12345678900", convertedUser.getCpf());
        assertEquals("Eduardo", convertedUser.getFirstName());
        assertEquals("Lublanski", convertedUser.getLastName());
        assertEquals("01001000", convertedUser.getCep());
        assertEquals("edulub", convertedUser.getUsername());
        assertEquals("123456", convertedUser.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenConvertingNullRequestDtoToUser() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> userMapper.toUser(null)
                );

        assertEquals(
                "UserRequestDto não pode ser nulo",
                exception.getMessage()
        );
    }

    @Test
    void shouldConvertUserToResponseDto() {
        UserResponseDto responseDto = userMapper.toResponseDto(user);

        assertNotNull(responseDto);
        assertEquals("abc123", responseDto.getId());
        assertEquals("12345678900", responseDto.getCpf());
        assertEquals("Eduardo", responseDto.getFirstName());
        assertEquals("Lublanski", responseDto.getLastName());
        assertEquals("01001000", responseDto.getCep());
        assertEquals("edulub", responseDto.getUsername());
    }

    @Test
    void shouldNotExposePasswordInResponseDto() {
        UserResponseDto responseDto = userMapper.toResponseDto(user);

        assertNotNull(responseDto);
        // implicitamente valida porque UserResponseDto não possui campo password
        assertEquals("edulub", responseDto.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenConvertingNullUserToResponseDto() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> userMapper.toResponseDto(null)
                );

        assertEquals(
                "User não pode ser nulo",
                exception.getMessage()
        );
    }

    @Test
    void shouldConvertRequestDtoListToUserList() {
        List<User> users =
                userMapper.toUserList(List.of(requestDto));

        assertEquals(1, users.size());
        assertEquals("Eduardo", users.getFirst().getFirstName());
        assertEquals("edulub", users.getFirst().getUsername());
    }

    @Test
    void shouldIgnoreNullItemsWhenConvertingRequestDtoList() {
        List<UserRequestDto> dtos = new ArrayList<>();
        dtos.add(requestDto);
        dtos.add(null);

        List<User> users = userMapper.toUserList(dtos);

        assertEquals(1, users.size());
        assertEquals("Eduardo", users.getFirst().getFirstName());
    }

    @Test
    void shouldReturnEmptyListWhenRequestDtoListIsNull() {
        List<User> users = userMapper.toUserList(null);

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    void shouldConvertUserListToResponseDtoList() {
        List<UserResponseDto> dtos =
                userMapper.toResponseDtoList(List.of(user));

        assertEquals(1, dtos.size());
        assertEquals("Eduardo", dtos.getFirst().getFirstName());
        assertEquals("edulub", dtos.getFirst().getUsername());
    }

    @Test
    void shouldIgnoreNullItemsWhenConvertingUserList() {
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(null);

        List<UserResponseDto> dtos =
                userMapper.toResponseDtoList(users);

        assertEquals(1, dtos.size());
        assertEquals("Eduardo", dtos.getFirst().getFirstName());
    }

    @Test
    void shouldReturnEmptyListWhenUserListIsNull() {
        List<UserResponseDto> dtos =
                userMapper.toResponseDtoList(null);

        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void shouldUpdateUser() {
        UserRequestDto updatedDto =
                UserRequestDto.builder()
                        .cpf("99999999999")
                        .firstName("Carlos")
                        .lastName("Silva")
                        .cep("02002000")
                        .username("carlos")
                        .password("abcdef")
                        .build();

        userMapper.updateUser(user, updatedDto);

        assertEquals("99999999999", user.getCpf());
        assertEquals("Carlos", user.getFirstName());
        assertEquals("Silva", user.getLastName());
        assertEquals("02002000", user.getCep());
        assertEquals("carlos", user.getUsername());
        assertEquals("abcdef", user.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithNullValues() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> userMapper.updateUser(null, requestDto)
                );

        assertEquals(
                "User ou UserRequestDto não pode ser nulo",
                exception.getMessage()
        );
    }
}