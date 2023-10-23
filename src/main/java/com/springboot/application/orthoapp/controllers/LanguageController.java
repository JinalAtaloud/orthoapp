package com.springboot.application.orthoapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.application.orthoapp.dto.LanguageRequest;
import com.springboot.application.orthoapp.exceptions.DataAlreadyExistsException;
import com.springboot.application.orthoapp.exceptions.DataNotFoundException;
import com.springboot.application.orthoapp.exceptions.UnsupportedFileFormatException;
import com.springboot.application.orthoapp.model.Language;
import com.springboot.application.orthoapp.repository.LanguageRepository;
import com.springboot.application.orthoapp.s3util.S3Util;
import com.springboot.application.orthoapp.services.OrthoappLanguageService;

@RestController
@RequestMapping("/api/v1/language")
public class LanguageController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	String message = "";
	private final ObjectMapper objectMapper;

	private LanguageRepository languageRepository;
	private OrthoappLanguageService orthoappLanguageService;
	private S3Util s3Util;

	public LanguageController(LanguageRepository languageRepository, OrthoappLanguageService orthoappLanguageService,
			S3Util s3Util, ObjectMapper objectMapper) {
		this.objectMapper = new ObjectMapper();
		this.languageRepository = languageRepository;
		this.orthoappLanguageService = orthoappLanguageService;
		this.s3Util = s3Util;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Language> add(@ModelAttribute LanguageRequest request) throws Exception {
		String language = request.getLanguage();
		String spokenLanguage = request.getSpokenLanguage();
		String code = request.getCode();
		MultipartFile multipartFile = request.getFile();
		Map<String, String> data = objectMapper.readValue(request.getJson(), new TypeReference<Map<String, String>>() {
		});
		String type=multipartFile.getContentType();
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		Language languageModel = orthoappLanguageService.retriveLanguageByName(language);
		if (languageModel != null) {
			throw new DataAlreadyExistsException("Language already exists");
		}

		 s3Util.uploadFile(fileName, multipartFile.getInputStream());
		 String fileObjectUrl = s3Util.createObjectUrl(fileName);
		message = "Your file has been uploaded sucessfully";
		Language response = new Language();
		response.setLanguage(language);
		response.setCode(code);
		response.setData(data);
		response.setSpokenLanguage(spokenLanguage);
		response.setSymbolFilename(fileName);
		response.setSymbolFileUrl(fileObjectUrl);
		languageRepository.save(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLanguage(@PathVariable("id") String id) {
		Language languageModel = orthoappLanguageService.getLanguageById(id);
		if (languageModel == null) {
			throw new DataNotFoundException("The langauage doesn't exist with id - " + id);
		} else {
			languageRepository.deleteById(id);
			message = "File deleted successfully!!";
			return new ResponseEntity<>(message, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String getLanguage(@PathVariable("id") String id) {
		Language languageModel = orthoappLanguageService.getLanguageById(id);
		if (languageModel == null) {
			throw new DataNotFoundException("The language doesn't exist with id - " + id);
		}
		return languageModel.getLanguage();

	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public List<Language> getLanguages() {
		List<Language> languages = orthoappLanguageService.getAllLanguages();
		if (languages == null) {
			throw new DataNotFoundException("There are no language(s) available");
		}
		return languages;

	}
	// TODO

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateLanguage(@PathVariable("id") String id,
			@RequestParam("language") String language) {
		String message = "";
		Language languageModel = orthoappLanguageService.getLanguageById(id);
		if (languageModel == null) {
			throw new DataNotFoundException("The language doesn't exist with id - " + id);
		}

		if (languageModel.getLanguage().equals(language)) {
			throw new DataAlreadyExistsException("Language already exists");
		}

		else {
			languageModel.setLanguage(language);
			orthoappLanguageService.updateLanguage(languageModel);
			message = "Language successfully updated";
			return new ResponseEntity<>(message, HttpStatus.OK);
		}

	}
}
