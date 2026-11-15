package com.project.studentmanagement.repository;

import com.project.studentmanagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository  extends JpaRepository<Users, Long> {

    boolean existsByUsername(String admin);

    Optional<Users> findByUsername(String username);
}
