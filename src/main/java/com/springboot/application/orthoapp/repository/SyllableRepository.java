package com.springboot.application.orthoapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.springboot.application.orthoapp.model.Syllable;
import com.springboot.application.orthoapp.model.SyllableType;

@Repository
public interface SyllableRepository extends MongoRepository<Syllable, String> {
	List<Syllable> findBySyallableType(SyllableType syallableType);
	
}
