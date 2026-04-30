package com.senacsp.projetosemestral.bibliotecapessoal.adapter.configuration.security;


import com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager.MembershipManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


//    @Bean
//    public CommandLineRunner initAdminLog(MembershipManager memberShipManager) {
//        return args -> {
//            long count = repository.count();
//            if (count == 0) {
//                System.out.println("Nenhum administrador cadastrado");
//            } else {
//                System.out.println("Administrador já cadastrado");
//            }
//        };
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("beauty-clinic-api/v1/adm/register").permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .httpBasic(basic -> {});

        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
