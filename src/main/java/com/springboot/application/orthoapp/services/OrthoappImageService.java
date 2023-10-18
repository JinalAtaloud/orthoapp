package com.springboot.application.orthoapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.application.orthoapp.model.Images;
import com.springboot.application.orthoapp.repository.ImageRepository;

@Service
public class OrthoappImageService {
	private ImageRepository imageRepository;

	public OrthoappImageService(ImageRepository imageRepository) {
		super();
		this.imageRepository = imageRepository;
	}

	public Images retrieveSpecificImage(String id) {
		return imageRepository.findById(id).isPresent() ? imageRepository.findById(id).get() : null;

	}

	public List<Images> retrieveAllImages() {
		return imageRepository.findAll().isEmpty()? null : imageRepository.findAll() ;
		
	}

	public Images getImageById(String id) {
		return imageRepository.findById(id).isPresent() ? imageRepository.findById(id).get() : null;
		
	}

}
