package com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence.repository.mongodb;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.mapper.UserMapper;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembershipManagerMongoImpTest {

    @Mock
    private UserMongoRepository userMongoRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MembershipManagerMongoImp membershipManager;

    private UserRequestDto requestDto;
    private User user;
    private UserResponseDto responseDto;

    @BeforeEach
    void setUp() {
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

        responseDto = UserResponseDto.builder()
                .id("abc123")
                .cpf("12345678900")
                .firstName("Eduardo")
                .lastName("Lublanski")
                .cep("01001000")
                .username("edulub")
                .build();
    }

    @Test
    void shouldAdmitUser() {
        when(userMapper.toUser(requestDto)).thenReturn(user);
        when(userMongoRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn(requestDto.getPassword());

        UserResponseDto result = membershipManager.admit(requestDto);

        assertNotNull(result);
        assertEquals("abc123", result.getId());
        assertEquals("Eduardo", result.getFirstName());

        verify(userMapper).toUser(requestDto);
        verify(userMongoRepository).save(user);
        verify(userMapper).toResponseDto(user);
    }

    @Test
    void shouldReturnAllMembers() {
        when(userMongoRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toResponseDtoList(List.of(user)))
                .thenReturn(List.of(responseDto));

        List<UserResponseDto> result = membershipManager.getMembers();

        assertEquals(1, result.size());
        assertEquals("Eduardo", result.getFirst().getFirstName());

        verify(userMongoRepository).findAll();
        verify(userMapper).toResponseDtoList(List.of(user));
    }

    @Test
    void shouldReturnMembershipDetails() {
        when(userMongoRepository.findById("abc123"))
                .thenReturn(Optional.of(user));

        when(userMapper.toResponseDto(user))
                .thenReturn(responseDto);

        UserResponseDto result =
                membershipManager.getMembershipDetails("abc123");

        assertNotNull(result);
        assertEquals("abc123", result.getId());

        verify(userMongoRepository).findById("abc123");
        verify(userMapper).toResponseDto(user);
    }

    @Test
    void shouldThrowExceptionWhenMembershipNotFound() {
        when(userMongoRepository.findById("999"))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> membershipManager.getMembershipDetails("999")
                );

        assertEquals(
                "não há membro cadastrado com id 999",
                exception.getMessage()
        );
    }

    @Test
    void shouldUpdateMembership() {
        when(userMongoRepository.findById("abc123"))
                .thenReturn(Optional.of(user));

        when(userMongoRepository.save(user))
                .thenReturn(user);

        when(passwordEncoder.encode(requestDto.getPassword()))
                .thenReturn(requestDto.getPassword());

        when(userMapper.toResponseDto(user))
                .thenReturn(responseDto);

        UserResponseDto result =
                membershipManager.updateInfo("abc123", requestDto);

        assertNotNull(result);
        assertEquals("abc123", result.getId());

        verify(userMongoRepository).findById("abc123");
        verify(userMapper).updateUser(user, requestDto);
        verify(userMongoRepository).save(user);
        verify(userMapper).toResponseDto(user);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingMembership() {
        when(userMongoRepository.findById("999"))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> membershipManager.updateInfo("999", requestDto)
                );

        assertEquals(
                "não há membro cadastrado com id 999",
                exception.getMessage()
        );
    }

    @Test
    void shouldRemoveMembership() {
        when(userMongoRepository.existsById("abc123"))
                .thenReturn(true);

        membershipManager.removeMembership("abc123");

        verify(userMongoRepository).deleteById("abc123");
    }

    @Test
    void shouldThrowExceptionWhenRemovingNonExistingMembership() {
        when(userMongoRepository.existsById("999"))
                .thenReturn(false);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> membershipManager.removeMembership("999")
                );

        assertEquals(
                "não há membro cadastrado com id 999",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnTrueWhenUsernameExists() {
        when(userMongoRepository.existsByUsername("edulub"))
                .thenReturn(true);

        boolean result =
                membershipManager.existsByUsername("edulub");

        assertTrue(result);

        verify(userMongoRepository).existsByUsername("edulub");
    }

    @Test
    void shouldReturnTrueWhenCpfExists() {
        when(userMongoRepository.existsByCpf("12345678900"))
                .thenReturn(true);

        boolean result =
                membershipManager.existsByCpf("12345678900");

        assertTrue(result);

        verify(userMongoRepository).existsByCpf("12345678900");
    }
}