package com.sovon9.Spring_Security.config.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sovon9.Spring_Security.config.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{

	public Optional<User> findByUsername(String username);
	
}
