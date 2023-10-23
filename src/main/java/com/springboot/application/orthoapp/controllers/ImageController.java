package com.springboot.application.orthoapp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.application.orthoapp.exceptions.DataNotFoundException;
import com.springboot.application.orthoapp.exceptions.UnsupportedFileFormatException;
import com.springboot.application.orthoapp.model.Images;
import com.springboot.application.orthoapp.model.Language;
import com.springboot.application.orthoapp.repository.ImageRepository;
import com.springboot.application.orthoapp.repository.LanguageRepository;
import com.springboot.application.orthoapp.s3util.S3Util;
import com.springboot.application.orthoapp.services.OrthoappImageService;
import com.springboot.application.orthoapp.services.OrthoappLanguageService;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	String message = "";

	private ImageRepository imageRepository;
	private OrthoappImageService orthoappImageService;
	private LanguageRepository languageRepository;
	private OrthoappLanguageService languageService;
	private S3Util s3Util;

	public ImageController(ImageRepository imageRepository, OrthoappImageService orthoappImageService,LanguageRepository languageRepository,OrthoappLanguageService languageService,S3Util s3Util) {
		super();
		this.imageRepository = imageRepository;
		this.orthoappImageService = orthoappImageService;
		this.languageRepository=languageRepository;
		this.languageService=languageService;
		this.s3Util=s3Util;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<Images> uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam("language_id")String language_id) throws Exception {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		List<String> fileTypes = new ArrayList<>();
		fileTypes.add("image/jpeg");
		fileTypes.add("image/png");
		String type=multipartFile.getContentType();
		logger.info("*********Content Type:{}",type);
		if (!fileTypes.contains(multipartFile.getContentType())) {
			throw new UnsupportedFileFormatException(
					"File format is not supported - " + multipartFile.getContentType());
		}
		
		Language language = languageService.getLanguageById(language_id);
		if(language==null) {
			throw new DataNotFoundException("Language id doesn't exist");
		}
		try {
			s3Util.uploadFile(fileName, multipartFile.getInputStream());
			message = "Your file has been uploaded sucessfully";
			Images response = new Images();
			String fileObjectUrl = s3Util.createObjectUrl(fileName);
			response.setFileName(fileName);
			response.setFileUrl(fileObjectUrl);
			response.setLanguage(language);
			imageRepository.save(response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			//throw new Exception(e);
			throw new Exception(e.getMessage());
		}

	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteVideo(@PathVariable("id") String id) {
		Images image = orthoappImageService.getImageById(id);
		if (image == null) {
			throw new DataNotFoundException("The image doesn't exist with id - " + id);
		} else {
			s3Util.deleteFile(image.getFileName());
			imageRepository.deleteById(id);
			message = "File deleted successfully!!";
			return new ResponseEntity<>(message, HttpStatus.OK);
		}

	}

	// get training video by id
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String getVideo(@PathVariable("id") String id,@RequestParam("language_id") Long language_id) {
		if(language_id == null){
			throw new DataNotFoundException("Language id is null");
			
		}
		
		Images image = orthoappImageService.retrieveSpecificImage(id);
		if (image == null) {
			throw new DataNotFoundException("The image doesn't exist with id - " + id);
		}
		return image.getFileUrl();

	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public List<Images> getVideo() {
		List<Images> images = orthoappImageService.retrieveAllImages();
		if (images == null) {
			throw new DataNotFoundException("There are no images available");
		}
		return images;

	}


}
