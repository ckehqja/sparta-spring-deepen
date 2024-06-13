package com.team13.fantree.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team13.fantree.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
