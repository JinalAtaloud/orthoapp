package com.springboot.application.orthoapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.application.orthoapp.model.Language;

@Repository
public interface LanguageRepository extends MongoRepository<Language, String> {
	
	Language findByLanguage(String language);



}
