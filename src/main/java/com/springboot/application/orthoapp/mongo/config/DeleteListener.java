package com.springboot.application.orthoapp.mongo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import com.springboot.application.orthoapp.model.Language;
import com.springboot.application.orthoapp.repository.ImageRepository;
import com.springboot.application.orthoapp.repository.TrainingVideosRepository;

@Component
public class DeleteListener extends AbstractMongoEventListener<Language>{

	@Autowired
	private ImageRepository imageRepository;
	private TrainingVideosRepository videoRepository;

	
	
	public DeleteListener(ImageRepository imageRepository, TrainingVideosRepository videoRepository) {
		super();
		this.imageRepository = imageRepository;
		this.videoRepository = videoRepository;
	}


	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Language> event) {
		String languageId = event.getSource().get("_id").toString();
		videoRepository.deleteByLanguageId(languageId);
		imageRepository.deleteByLanguageId(languageId);
	}
	
	
	
}
