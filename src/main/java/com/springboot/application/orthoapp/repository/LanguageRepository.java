package com.springboot.application.orthoapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.application.orthoapp.model.Languages;

@Repository
public interface LanguageRepository extends MongoRepository<Languages, String> {
	
	Languages findByLanguage(String language);



}
