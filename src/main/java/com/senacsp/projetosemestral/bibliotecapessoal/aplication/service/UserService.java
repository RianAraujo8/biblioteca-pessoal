package com.senacsp.projetosemestral.bibliotecapessoal.aplication.service;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager.MembershipManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final MembershipManager membershipManager;

    public UserResponseDto register(UserRequestDto dto) { return membershipManager.admit(dto); }

    public List<UserResponseDto> list() { return membershipManager.getMembers(); }

    public UserResponseDto getById(String id) { return membershipManager.getMembershipDetails(id); }

    public UserResponseDto updateById(String id, UserRequestDto dto) { return membershipManager.updateInfo(id, dto); }

    public void delete(String id) { membershipManager.removeMembership(id); }

    public boolean usernameAlreadyExists(String username) { return membershipManager.existsByUsername(username); }

    public boolean cpfAlreadyExists(String cpf) { return membershipManager.existsByCpf(cpf); }
}
