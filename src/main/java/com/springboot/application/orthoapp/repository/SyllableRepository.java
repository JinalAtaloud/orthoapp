package com.springboot.application.orthoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.application.orthoapp.model.Syllable;
import com.springboot.application.orthoapp.model.SyllableType;

@Repository
public interface SyllableRepository extends JpaRepository<Syllable, Long> {
	List<Syllable> findBySyallableType(SyllableType syallableType);
	
}
