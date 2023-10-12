package com.springboot.application.orthoapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.application.orthoapp.dto.SyllableDto;
import com.springboot.application.orthoapp.model.Syllable;
import com.springboot.application.orthoapp.model.SyllableType;
import com.springboot.application.orthoapp.repository.SyllableRepository;

@Service
public class OrthoappSyllableService {

	@Autowired
	private SyllableRepository syllableRepository;
	
	public List<SyllableDto> getAllSyllables(){
		List<Syllable> syllables = syllableRepository.findAll();
		return syllables.stream().map(this::convertToDTO).collect(Collectors.toList());
		
	}
	
	public List<SyllableDto> getSyllablesByType(SyllableType syllableType){
		List<Syllable> syllables = syllableRepository.findBySyallableType(syllableType);
		return syllables.stream().map(this::convertToDTO).collect(Collectors.toList());
		
	}
	
	
	private SyllableDto convertToDTO(Syllable syllable) {
		SyllableDto dto = new SyllableDto();
		dto.setId(syllable.getId());
		dto.setImageId(syllable.getImage().getId());
		dto.setImageObjectUrl(syllable.getImage().getFileUrl());
		dto.setLanguageId(syllable.getImage().getLanguage().getId());
		dto.setUserId(syllable.getUser().getId());
		dto.setSyllableType(syllable.getSyallableType());
		return dto;
	}

}
