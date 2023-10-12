package com.springboot.application.orthoapp.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.application.orthoapp.exceptions.DataAlreadyExistsException;
import com.springboot.application.orthoapp.exceptions.DataNotFoundException;
import com.springboot.application.orthoapp.model.Languages;
import com.springboot.application.orthoapp.repository.LanguageRepository;
import com.springboot.application.orthoapp.services.OrthoappLanguageService;

@RestController
@RequestMapping("/api/v1/language")
public class LanguageController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	String message = "";

	private LanguageRepository languageRepository;
	private OrthoappLanguageService orthoappLanguageService;

	public LanguageController(LanguageRepository languageRepository, OrthoappLanguageService orthoappLanguageService) {
		super();
		this.languageRepository = languageRepository;
		this.orthoappLanguageService = orthoappLanguageService;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Languages> add(@RequestBody Map<String, Object> requestMap) throws Exception {
		String language= (String) requestMap.get("language");
		String code = (String) requestMap.get("code");
		Map<String,String> jsonObject = (Map<String, String>) requestMap.get("jsonObject");
		Languages languageModel = orthoappLanguageService.retriveLanguageByName(language);
		if (languageModel != null) {
			throw new DataAlreadyExistsException("Language already exists");
		}
		
		message = "Your file has been uploaded sucessfully";
		Languages response = new Languages();
		response.setLanguage(language);
		response.setCode(code);
		response.setData(jsonObject);
		languageRepository.save(response);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLanguage(@PathVariable("id") Integer id) {
		Languages languageModel = orthoappLanguageService.getLanguageById(id);
		if (languageModel == null) {
			throw new DataNotFoundException("The langauage doesn't exist with id - " + id);
		} else {
			languageRepository.deleteById(id);
			message = "File deleted successfully!!";
			return new ResponseEntity<>(message, HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String getLanguage(@PathVariable("id") Integer id) {
		Languages languageModel = orthoappLanguageService.getLanguageById(id);
		if (languageModel == null) {
			throw new DataNotFoundException("The language doesn't exist with id - " + id);
		}
		return languageModel.getLanguage();

	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public List<Languages> getLanguages() {
		List<Languages> languages = orthoappLanguageService.getAllLanguages();
		if (languages == null) {
			throw new DataNotFoundException("There are no language(s) available");
		}
		return languages;

	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateLanguage(@PathVariable("id") Integer id,
			@RequestParam("language") String language) {
		String message = "";
		Languages languageModel = orthoappLanguageService.getLanguageById(id);
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
