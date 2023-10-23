package com.springboot.application.orthoapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.springboot.application.orthoapp.model.Images;
import com.springboot.application.orthoapp.model.Language;

@Repository
public interface ImageRepository extends MongoRepository<Images, String> {

	//Videos findByFileName(String fileName);

	List<Language> findByLanguageId(String languageId);
	void deleteByLanguageId(String languageId);

}
