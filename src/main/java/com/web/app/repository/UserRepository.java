package com.web.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.app.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);
	
	@Query(value ="select * from user where username=?", nativeQuery = true)
	User checkUsername(String username);
	
	@Query(value ="select * from user where id=?", nativeQuery = true)
	User getUserById(int id);
}
