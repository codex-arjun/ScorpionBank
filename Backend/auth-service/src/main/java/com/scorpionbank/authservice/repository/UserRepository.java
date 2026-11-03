package com.scorpionbank.authservice.repository;

import com.scorpionbank.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailIgnoreCase(String Email);

    boolean existsByEmailIgnoreCase(String email);
}
