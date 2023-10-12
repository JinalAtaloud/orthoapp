package com.springboot.application.orthoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.application.orthoapp.model.Videos;

import jakarta.transaction.Transactional;

@Repository
public interface TrainingVideosRepository extends JpaRepository<Videos, Long> {

	Videos findByFileName(String fileName);
	
	@Modifying
    @Transactional
	@Query("update Videos c set c.lastModifiedDate = :lastModifiedDate WHERE c.id = :id")
    void updateVideoDetails(@Param("id") Long id, @Param("lastModifiedDate") String lastModifiedDate);

//	
//	@Query("select id, fileUrl, title, description, lastModifiedDate, fileName from TrainingVideoModel c where c.id = :id and c.language.id =:language_id")
//    TrainingVideoModel findVideo(@Param("id") Long id, @Param("language_id") Long language_id);
}
