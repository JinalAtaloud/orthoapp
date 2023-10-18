//Step 3
package com.springboot.application.orthoapp.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{
	
	Optional<User> findByEmail(String email);
	Optional<User> findById(Long user_id);
	
}
