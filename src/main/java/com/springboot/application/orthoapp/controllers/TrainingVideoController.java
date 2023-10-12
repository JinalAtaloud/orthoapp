package com.springboot.application.orthoapp.controllers;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.application.orthoapp.exceptions.DataNotFoundException;
import com.springboot.application.orthoapp.exceptions.UnsupportedFileFormatException;
import com.springboot.application.orthoapp.model.Languages;
import com.springboot.application.orthoapp.model.Videos;
import com.springboot.application.orthoapp.repository.LanguageRepository;
import com.springboot.application.orthoapp.repository.TrainingVideosRepository;
import com.springboot.application.orthoapp.s3util.S3Util;
import com.springboot.application.orthoapp.services.OrthoappLanguageService;
import com.springboot.application.orthoapp.services.OrthoappVideoService;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.S3Exception;

@RestController
@RequestMapping("/api/v1/video")
public class TrainingVideoController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	String message = "";

	private TrainingVideosRepository videoRepository;
	private OrthoappVideoService orthoappService;
	private LanguageRepository languageRepository;
	private OrthoappLanguageService languageService;

	public TrainingVideoController(TrainingVideosRepository videoRepository, OrthoappVideoService orthoappService,LanguageRepository languageRepository,OrthoappLanguageService languageService) {
		super();
		this.videoRepository = videoRepository;
		this.orthoappService = orthoappService;
		this.languageRepository=languageRepository;
		this.languageService=languageService;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<Videos> uploadFile(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("description") String description, @RequestParam("title") String title,@RequestParam("language_id")Integer language_id) throws Exception {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		List<String> fileTypes = new ArrayList<>();
		fileTypes.add("video/mp4");
		String type = multipartFile.getContentType();
		Languages language = languageService.getLanguageById(language_id);
		if(language==null) {
			throw new DataNotFoundException("Language id doesn't exist");
		}
		if (!fileTypes.contains(multipartFile.getContentType())) {
			throw new UnsupportedFileFormatException(
					"File format is not supported - " + multipartFile.getContentType());
		}
		try {
			S3Util.uploadFile(fileName, multipartFile.getInputStream());
			message = "Your file has been uploaded sucessfully";
			Videos response = new Videos();
			LocalDateTime lastModifiedDate = S3Util.getLastModifiedDate(fileName);
			String formatDateTime = lastModifiedDate.format(format);
			String fileObjectUrl = S3Util.createObjectUrl(fileName);
			
			response.setFileName(fileName);
			response.setFileUrl(fileObjectUrl);
			response.setDescription(description);
			response.setTitle(title);
			response.setLastModifiedDate(formatDateTime);
			response.setLanguage(language);
			videoRepository.save(response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception(e);
			// throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			// message = "Error uploading file: "+e.getMessage();
		}

	}

	// delete a video by id
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteVideo(@PathVariable("id") Long id) {
		Videos trainingVideo = orthoappService.getTrainingVideoById(id);
		if (trainingVideo == null) {
			throw new DataNotFoundException("The vidoe doesn't exist with id - " + id);
		} else {
			S3Util.deleteFile(trainingVideo.getFileName());
			videoRepository.deleteById(id);
			message = "File deleted successfully!!";
			return new ResponseEntity<>(message, HttpStatus.OK);
		}

	}

	// get training video by id
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String getVideo(@PathVariable("id") Long id,@RequestParam("language_id") Long language_id) {		
		
		if(language_id == null && language_id==0l){
			throw new DataNotFoundException("Language id is null");
			
		}
		Videos video = orthoappService.retrieveSpecificTrainingVideo(id,language_id);
		System.out.println("Video:{}"+video);
		if (video == null) {
			throw new DataNotFoundException("The video doesn't exist with id - " + id);
		}
		return video.getFileUrl();

	}

	// get all training videos
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public List<Videos> getVideo() {
		List<Videos> videos = orthoappService.retrieveAllTrainingVideos();
		if (videos == null) {
			throw new DataNotFoundException("There are no videos available");
		}
		return videos;

	}

	// update a video
	@RequestMapping(value = "/update/{filename}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateVideo(@PathVariable("filename") String filename,
			@RequestParam("file") MultipartFile multipartFile)
			throws S3Exception, AwsServiceException, SdkClientException, IOException {
		String message = "";
		Videos trainingVideo = orthoappService.getTrainingVideoByFilename(filename);
		if (trainingVideo == null) {
			throw new DataNotFoundException("The video doesn't exist with name - " + filename);
		} else {
			S3Util.updateFile(trainingVideo.getFileUrl(), multipartFile.getInputStream());
			LocalDateTime lastModifiedDate = S3Util.getLastModifiedDate(trainingVideo.getFileUrl());
			String formatDateTime = lastModifiedDate.format(format);
			System.out.println("formatDateTime " + formatDateTime);
			trainingVideo.setLastModifiedDate(formatDateTime);
			orthoappService.updateTrainingVideo(trainingVideo);
			message = "Video successfully updated";
			return new ResponseEntity<>(message, HttpStatus.OK);
		}

	}
}
