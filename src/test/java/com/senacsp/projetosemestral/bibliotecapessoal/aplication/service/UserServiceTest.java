package com.senacsp.projetosemestral.bibliotecapessoal.aplication.service;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager.MembershipManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private MembershipManager membershipManager;

    @InjectMocks
    private UserService userService;

    private UserRequestDto requestDto;
    private UserResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = UserRequestDto.builder()
                .cpf("12345678900")
                .firstName("Eduardo")
                .lastName("Lublanski")
                .cep("01001-000")
                .username("eduardo.dev")
                .password("123456")
                .build();

        responseDto = UserResponseDto.builder()
                .id("abc123")
                .cpf("12345678900")
                .firstName("Eduardo")
                .lastName("Lublanski")
                .cep("01001-000")
                .username("eduardo.dev")
                .build();
    }

    @Test
    void shouldRegisterUser() {
        when(membershipManager.admit(requestDto)).thenReturn(responseDto);

        UserResponseDto result = userService.register(requestDto);

        assertNotNull(result);
        assertEquals("abc123", result.getId());
        assertEquals("Eduardo", result.getFirstName());

        verify(membershipManager).admit(requestDto);
    }

    @Test
    void shouldListUsers() {
        when(membershipManager.getMembers()).thenReturn(List.of(responseDto));

        List<UserResponseDto> result = userService.list();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("eduardo.dev", result.getFirst().getUsername());

        verify(membershipManager).getMembers();
    }

    @Test
    void shouldGetUserById() {
        when(membershipManager.getMembershipDetails("abc123"))
                .thenReturn(responseDto);

        UserResponseDto result = userService.getById("abc123");

        assertNotNull(result);
        assertEquals("abc123", result.getId());

        verify(membershipManager).getMembershipDetails("abc123");
    }

    @Test
    void shouldUpdateUser() {
        UserRequestDto updatedDto = UserRequestDto.builder()
                .cpf("12345678900")
                .firstName("Carlos")
                .lastName("Lublanski")
                .cep("01001-000")
                .username("carlos.dev")
                .password("654321")
                .build();

        UserResponseDto updatedResponse = UserResponseDto.builder()
                .id("abc123")
                .cpf("12345678900")
                .firstName("Carlos")
                .lastName("Lublanski")
                .cep("01001-000")
                .username("carlos.dev")
                .build();

        when(membershipManager.updateInfo("abc123", updatedDto))
                .thenReturn(updatedResponse);

        UserResponseDto result =
                userService.updateById("abc123", updatedDto);

        assertNotNull(result);
        assertEquals("Carlos", result.getFirstName());
        assertEquals("carlos.dev", result.getUsername());

        verify(membershipManager).updateInfo("abc123", updatedDto);
    }

    @Test
    void shouldDeleteUser() {
        doNothing().when(membershipManager).removeMembership("abc123");

        assertDoesNotThrow(() ->
                userService.delete("abc123"));

        verify(membershipManager).removeMembership("abc123");
    }

    @Test
    void shouldReturnTrueWhenUsernameAlreadyExists() {
        when(membershipManager.existsByUsername("eduardo.dev"))
                .thenReturn(true);

        boolean result =
                userService.usernameAlreadyExists("eduardo.dev");

        assertTrue(result);

        verify(membershipManager).existsByUsername("eduardo.dev");
    }

    @Test
    void shouldReturnFalseWhenUsernameDoesNotExist() {
        when(membershipManager.existsByUsername("ghost"))
                .thenReturn(false);

        boolean result =
                userService.usernameAlreadyExists("ghost");

        assertFalse(result);

        verify(membershipManager).existsByUsername("ghost");
    }

    @Test
    void shouldReturnTrueWhenCpfAlreadyExists() {
        when(membershipManager.existsByCpf("12345678900"))
                .thenReturn(true);

        boolean result =
                userService.cpfAlreadyExists("12345678900");

        assertTrue(result);

        verify(membershipManager).existsByCpf("12345678900");
    }

    @Test
    void shouldReturnFalseWhenCpfDoesNotExist() {
        when(membershipManager.existsByCpf("00000000000"))
                .thenReturn(false);

        boolean result =
                userService.cpfAlreadyExists("00000000000");

        assertFalse(result);

        verify(membershipManager).existsByCpf("00000000000");
    }
}