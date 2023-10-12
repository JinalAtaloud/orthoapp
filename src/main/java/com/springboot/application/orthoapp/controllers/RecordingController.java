//package com.springboot.application.orthoapp.controllers;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.springboot.application.orthoapp.exceptions.DataNotFoundException;
//import com.springboot.application.orthoapp.model.Images;
//import com.springboot.application.orthoapp.model.Languages;
//import com.springboot.application.orthoapp.model.Recording;
//import com.springboot.application.orthoapp.repository.ImageRepository;
//import com.springboot.application.orthoapp.repository.LanguageRepository;
//import com.springboot.application.orthoapp.repository.RecordingRepository;
//import com.springboot.application.orthoapp.s3util.S3Util;
//import com.springboot.application.orthoapp.services.OrthoappImageService;
//import com.springboot.application.orthoapp.services.OrthoappLanguageService;
//import com.springboot.application.orthoapp.services.RecordingService;
//
//@RestController
//@RequestMapping("api/v1/recordings")
//public class RecordingController {
//	
//	private LanguageRepository languageRepository;
//	private OrthoappLanguageService languageService;
//	private ImageRepository imageRepository;
//	private OrthoappImageService orthoappImageService;
//	private RecordingService recordingService;
//	private RecordingRepository repository;
//	
//	
//
//	public RecordingController(LanguageRepository languageRepository, OrthoappLanguageService languageService,
//			ImageRepository imageRepository, OrthoappImageService orthoappImageService,RecordingRepository repository,RecordingService recordingService) {
//		super();
//		this.languageRepository = languageRepository;
//		this.languageService = languageService;
//		this.imageRepository = imageRepository;
//		this.orthoappImageService = orthoappImageService;
//		this.repository=repository;
//		this.recordingService=recordingService;
//	}
//
//
//
//	/*@RequestMapping(value = "/saveAudio", method = RequestMethod.POST)
//	public ResponseEntity<String> saveAudio(@RequestParam("language_id") Long language_id,@RequestParam("image_id") Long image_id) {
//		//check if language exists
//		//language id should not be null
//		//fetch image from id
//		//record
//		//save in S3 bucket
//		
//		Recording recording = new Recording();
//		if(language_id==null) {
//			throw new DataNotFoundException("Language id cannot be null");
//		}
//		
//		LanguageModel language = languageService.getLanguageById(language_id);
//		if(language==null) {
//			throw new DataNotFoundException("Language id does not exists");
//		}
//		
//		ImageModel image = orthoappImageService.getImageById(image_id);
//		if(image==null) {
//			throw new DataNotFoundException("Language id does not exists");
//		}
//		
//		
//		try {
//		//System.out.println("recording.getId():"+recording.getId());
//		RecordingUtil.saveRecording();
//		
//		recording.setFilename("Recording");
//		recording.setImage(image);
//		recording.setLanguage(language);
//		
//		
//		
//		}catch(Exception e) {
//			System.out.println("Exception:{}"+e.getMessage());
//		}
//		
//		return null;
//	}*/
//	
//	@PostMapping("/start") public ResponseEntity<String> startRecording(@RequestParam("language_id") Integer language_id,@RequestParam("image_id") Long image_id){
//		//Recording recording = new Recording();
//		if(language_id==null) {
//			throw new DataNotFoundException("Language id cannot be null");
//		}
//		
//		Languages language = languageService.getLanguageById(language_id);
//		if(language==null) {
//			throw new DataNotFoundException("Language id does not exists");
//		}
//		
//		Images image = orthoappImageService.getImageById(image_id);
//		if(image==null) {
//			throw new DataNotFoundException("Language id does not exists");
//		}
//		
//		try {
//			System.out.println("Started Recording");
//			String filename = recordingService.startRecording(language,image);
//			Recording recording = new Recording();
//			recording.setFilename(filename);
//			recording.setLanguage(language);
//			recording.setImage(image);
//			recording.setFileObjectUrl(S3Util.createObjectUrl(filename));
//			recording.setCreatedDate(new java.util.Date());
//			repository.save(recording);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//		return ResponseEntity.ok("Recording...");
//	}
//	
//	@PostMapping("/stop") public ResponseEntity<String> stopRecording() throws Exception{
//		recordingService.stopRecording();
//		return ResponseEntity.ok("Recording stopped and saved.");
//	}
//
//}
