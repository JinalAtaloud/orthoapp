package com.springboot.application.orthoapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.springboot.application.orthoapp.model.Language;
import com.springboot.application.orthoapp.model.Videos;

@Repository
public interface TrainingVideosRepository extends MongoRepository<Videos, String> {

	Videos findByFileName(String fileName);
	List<Language> findByLanguageId(String languageId);
	void deleteByLanguageId(String languageId);
	
	//TODO
//	@Modifying
//    @Transactional
//	@Query("update Videos c set c.lastModifiedDate = :lastModifiedDate WHERE c.id = :id")
//    void updateVideoDetails(@Param("id") Long id, @Param("lastModifiedDate") String lastModifiedDate);

//	
//	@Query("select id, fileUrl, title, description, lastModifiedDate, fileName from TrainingVideoModel c where c.id = :id and c.language.id =:language_id")
//    TrainingVideoModel findVideo(@Param("id") Long id, @Param("language_id") Long language_id);
}
