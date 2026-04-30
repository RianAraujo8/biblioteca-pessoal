package com.senacsp.projetosemestral.bibliotecapessoal.adapter.configuration.security;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence.repository.mongodb.UserMongoRepository;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserMongoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException(
                        "não há membro cadastrado com username " + username));

        return new UserDetailsImp(user);
    }
}
