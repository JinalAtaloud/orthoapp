package com.springboot.application.orthoapp.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.application.orthoapp.exceptions.DataNotFoundException;
import com.springboot.application.orthoapp.model.Images;
import com.springboot.application.orthoapp.model.Recording;
import com.springboot.application.orthoapp.repository.RecordingRepository;
import com.springboot.application.orthoapp.s3util.S3Util;
import com.springboot.application.orthoapp.services.OrthoappImageService;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.S3Exception;

@RestController
@RequestMapping("api/v1/recording")
public class RecordingController {
	
	@Autowired
	private S3Util s3Util;
	private RecordingRepository recordingRepository;
	private OrthoappImageService orthoappImageService;
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	
	public RecordingController(S3Util s3Util,RecordingRepository recordingRepository,OrthoappImageService orthoappImageService) {
		this.s3Util = s3Util;
		this.recordingRepository=recordingRepository;
		this.orthoappImageService=orthoappImageService;
	}


	@PostMapping("/upload/{image_id}") public ResponseEntity<String> uploadAudioFile(@RequestBody byte[] audioData,@PathVariable String image_id) throws S3Exception, AwsServiceException, SdkClientException, IOException{
		Images image = orthoappImageService.getImageById(image_id);
		if(image==null) {
			throw new DataNotFoundException("Image id doesn't exist");
		}
		String objectKey = "audio-"+System.currentTimeMillis()+ ".wav";
		Recording recording = new Recording();
		InputStream inputStream = new ByteArrayInputStream(audioData);
//		LocalDateTime lastModifiedDate = s3Util.getLastModifiedDate(objectKey);
//		String formatDateTime = lastModifiedDate.format(format);
		String fileObjectUrl = s3Util.createObjectUrl(objectKey);
		s3Util.uploadFile(objectKey, inputStream);
		recording.setFilename(objectKey);
		//recording.setCreatedDate(formatDateTime);
		recording.setFileObjectUrl(fileObjectUrl);
		recording.setImage(image);
		recordingRepository.save(recording);
		return ResponseEntity.ok("Audio file uploaded!");
		
	}
	
	@GetMapping("/download/{objectKey}") public ResponseEntity<String> downloadAudioFileFromS3(@PathVariable String objectKey) throws IOException{
		
		String base64Audio = s3Util.getObject(objectKey);
		return ResponseEntity.ok(base64Audio);
		
	}
}


