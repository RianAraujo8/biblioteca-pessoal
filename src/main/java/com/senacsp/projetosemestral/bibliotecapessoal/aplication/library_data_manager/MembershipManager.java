package com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserResponseDto;

import java.util.List;

public interface MembershipManager {

    UserResponseDto admit(UserRequestDto dto);

    List<UserResponseDto> getMembers();

    UserResponseDto getMembershipDetails(String id);

    UserResponseDto updateInfo(String id, UserRequestDto dto);

    void removeMembership(String id);

    boolean existsByUsername(String username);

    boolean existsByCpf(String cpf);
}
