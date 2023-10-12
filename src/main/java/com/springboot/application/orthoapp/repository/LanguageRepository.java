package com.springboot.application.orthoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.application.orthoapp.model.Languages;

import jakarta.transaction.Transactional;

@Repository
public interface LanguageRepository extends JpaRepository<Languages, Integer> {
	
	Languages findByLanguage(String language);
	
	@Modifying
    @Transactional
	@Query("update Languages c set c.language = :language WHERE c.id = :id")
    void updateLanguage(@Param("id") Long id, @Param("language") String language);

}
