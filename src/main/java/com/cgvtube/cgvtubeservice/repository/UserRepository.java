package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entiny.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
