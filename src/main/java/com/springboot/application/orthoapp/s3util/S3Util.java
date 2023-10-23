package com.springboot.application.orthoapp.s3util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;

import javax.sound.sampled.AudioInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.utils.IoUtils;

@Component
public class S3Util {

	private static final String BUCKET = "orthoapp-poc";
	private static final String REGION = "ap-south-1";


	public void uploadFile(String filename, InputStream inputStream)
			throws S3Exception, AwsServiceException, SdkClientException, IOException {
		S3Client client = S3Client.builder().build();
		PutObjectRequest request = PutObjectRequest.builder().bucket(BUCKET).key(filename).build();
		client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
	}
	
	

	public void deleteFile(String key) {
		S3Client client = S3Client.builder().build();
		DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(BUCKET).key(key).build();

		client.deleteObject(request);
	}

	
	public  void updateFile(String filename,InputStream inputStream) throws S3Exception, AwsServiceException, SdkClientException, IOException {
		deleteFile(filename);
		uploadFile(filename,inputStream);
		
	}

	public LocalDateTime getLastModifiedDate(String filename) {
		S3Client client = S3Client.builder().build();
		LocalDateTime ldt = null;

		ListObjectsRequest request = ListObjectsRequest.builder().bucket(BUCKET).build();

		ListObjectsResponse response = client.listObjects(request);
		List<S3Object> objects = response.contents();
		
		for(S3Object object:objects) {
			if(object.key().equals(filename)) {
				Instant lastModifiedDate =object.lastModified();
			
				ldt = LocalDateTime.ofInstant(lastModifiedDate, ZoneOffset.UTC);
			}
		}
		
		return ldt;
	}
	
	public  String createObjectUrl(String filename) {
		String objectURL = MessageFormat.format("https://{0}.s3.{1}.amazonaws.com/{2}",BUCKET,REGION,filename);
		return objectURL;
		
	}
	

	public String getObject(String objectKey) throws IOException {
		AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
		com.amazonaws.services.s3.model.S3Object s3Object = s3Client.getObject(BUCKET,objectKey);
		InputStream inputStream = s3Object.getObjectContent();
		byte[] audioBytes = IoUtils.toByteArray(inputStream);
		inputStream.close();
		String base64Audio = Base64.getEncoder().encodeToString(audioBytes);
		return base64Audio;
		
	}
	
	

	
}
