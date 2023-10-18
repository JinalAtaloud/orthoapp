package com.springboot.application.orthoapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.application.orthoapp.model.Videos;
import com.springboot.application.orthoapp.repository.TrainingVideosRepository;

@Service
public class OrthoappVideoService {
	private TrainingVideosRepository videoRepository;

	public OrthoappVideoService(TrainingVideosRepository videoRepository) {
		super();
		this.videoRepository = videoRepository;
	}

	public Videos retrieveSpecificTrainingVideo(String id, String language_id) {
		
		return videoRepository.findById(id).isPresent() ? videoRepository.findById(id).get(): null;

	}


	public List<Videos> retrieveAllTrainingVideos() {
		return videoRepository.findAll().isEmpty()? null : videoRepository.findAll() ;
		
	}

	public Videos getTrainingVideoById(String id) {
		return videoRepository.findById(id).isPresent() ? videoRepository.findById(id).get(): null;
	}

	public Videos getTrainingVideoByFilename(String filename) {
		return videoRepository.findByFileName(filename);
	}
	
	//TODO
	
	public void updateTrainingVideo(Videos trainingVideo) {
		 videoRepository.save(trainingVideo);
		
	}
			
}
