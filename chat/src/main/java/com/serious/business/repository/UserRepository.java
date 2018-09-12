package com.serious.business.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.serious.business.repository.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User AS u WHERE u.email = ?1")
	Optional<User> fetchByEmail(String email);
	
}
