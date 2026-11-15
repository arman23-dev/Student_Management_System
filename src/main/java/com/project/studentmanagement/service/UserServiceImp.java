package com.project.studentmanagement.service;

import com.project.studentmanagement.model.Users;
import com.project.studentmanagement.repository.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserDetailsService {
    private UsersRepository usersRepository;
    public UserServiceImp(UsersRepository usersRepository){
        this.usersRepository=usersRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Invalid User"));

        return User.withUsername(username)
                .password(user.getPassword())
                .disabled(!user.isActive())
                .build();
    }
}
