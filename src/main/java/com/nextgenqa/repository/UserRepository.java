package com.nextgenqa.repository;

import com.nextgenqa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para operações com a entidade User.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
