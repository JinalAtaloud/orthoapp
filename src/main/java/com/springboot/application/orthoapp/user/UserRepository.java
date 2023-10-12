//Step 3
package com.springboot.application.orthoapp.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);
	Optional<User> findById(Long user_id);
	
}
