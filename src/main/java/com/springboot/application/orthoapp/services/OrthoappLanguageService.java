package com.springboot.application.orthoapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.application.orthoapp.model.Language;
import com.springboot.application.orthoapp.repository.LanguageRepository;

@Service
public class OrthoappLanguageService {
	private LanguageRepository languageRepository;

	public OrthoappLanguageService(LanguageRepository languageRepository) {
		super();
		this.languageRepository = languageRepository;
	}
	
	public Language retriveLanguageByName(String language) {
		return languageRepository.findByLanguage(language);

	}

	public List<Language> getAllLanguages() {
		return languageRepository.findAll().isEmpty()? null : languageRepository.findAll() ;
		
	}

	public Language getLanguageById(String id) {
		return languageRepository.findById(id).isPresent() ? languageRepository.findById(id).get(): null;
	}


	//TODO
	public void updateLanguage(Language languageModel) {
		languageRepository.save(languageModel);
		
	}
	
	
	
}
