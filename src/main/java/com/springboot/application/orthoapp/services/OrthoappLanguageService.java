package com.springboot.application.orthoapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.application.orthoapp.model.Languages;
import com.springboot.application.orthoapp.repository.LanguageRepository;

@Service
public class OrthoappLanguageService {
	private LanguageRepository languageRepository;

	public OrthoappLanguageService(LanguageRepository languageRepository) {
		super();
		this.languageRepository = languageRepository;
	}
	
	public Languages retriveLanguageByName(String language) {
		return languageRepository.findByLanguage(language);

	}

	public List<Languages> getAllLanguages() {
		return languageRepository.findAll().isEmpty()? null : languageRepository.findAll() ;
		
	}

	public Languages getLanguageById(Integer id) {
		return languageRepository.findById(id).isPresent() ? languageRepository.findById(id).get(): null;
	}

	public void updateLanguage(Languages languageModel) {
		languageRepository.updateLanguage(languageModel.getId(), languageModel.getLanguage());
		
	}
	
	
	
}
