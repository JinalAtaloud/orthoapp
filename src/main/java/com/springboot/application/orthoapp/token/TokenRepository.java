package com.springboot.application.orthoapp.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TokenRepository extends MongoRepository<Token, String> {
	 @Query(value = " {'id' : ?0, 'token' : { $elemMatch: { 'expired' : false, 'revoked' : false}}}")
		  List<Token> findAllValidTokenByUser(String id);

		  Optional<Token> findByToken(String token);
}
