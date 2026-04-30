package com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence.repository.mongodb;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.mapper.UserMapper;
import com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager.MembershipManager;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MembershipManagerMongoImp implements MembershipManager {

    private final UserMongoRepository userMongoRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto admit(UserRequestDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);
        User newUser = userMapper.toUser(dto);
        User savedUser = userMongoRepository.save(newUser);

        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getMembers() {
        List<User> members = userMongoRepository.findAll();

        return userMapper.toResponseDtoList(members);
    }

    @Override
    public UserResponseDto getMembershipDetails(String id) {
        User user = userMongoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "não há membro cadastrado com id " + id));

        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto updateInfo(String id, UserRequestDto dto) {
        User userToUpdate = userMongoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "não há membro cadastrado com id " + id));

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        dto.setPassword(encodedPassword);

        userMapper.updateUser(userToUpdate, dto);

        User updatedUser = userMongoRepository.save(userToUpdate);

        return userMapper.toResponseDto(updatedUser);
    }

    @Override
    public void removeMembership(String id) {
        if (!userMongoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "não há membro cadastrado com id " + id);
        }

        userMongoRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userMongoRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return userMongoRepository.existsByCpf(cpf);
    }
}
