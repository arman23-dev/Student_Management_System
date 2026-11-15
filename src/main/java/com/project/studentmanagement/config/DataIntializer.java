package com.project.studentmanagement.config;

import com.project.studentmanagement.model.Users;
import com.project.studentmanagement.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataIntializer {

    @Bean
    CommandLineRunner loadsampledata(UsersRepository usersRepository,
                                     PasswordEncoder passwordEncoder){
        return args -> {
            if (!usersRepository.existsByUsername("Admin")) {
                Users users = new Users();
                users.setUsername("Admin");
                users.setPassword(passwordEncoder.encode("Admin@123"));
                users.setActive(true);
                usersRepository.save(users);
            }
        };
    }
}
