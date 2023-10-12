package com.springboot.application.orthoapp.s3util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import javax.sound.sampled.AudioInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectResult;

import jakarta.annotation.Resource;

@Component
public class S3Util {

	private static final String BUCKET = "orthoapp-bucket-v1";
	private static final String REGION = "ap-south-1";

	@Value("${audio.files.temp.location}") 
	private static String templateLocation;

	public static void uploadFile(String filename, InputStream inputStream)
			throws S3Exception, AwsServiceException, SdkClientException, IOException {
		S3Client client = S3Client.builder().build();
		System.out.println("Inside upload file");

		PutObjectRequest request = PutObjectRequest.builder().bucket(BUCKET).key(filename).acl("public-read").build();

		System.out.println("before put");
		client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
		System.out.println("After put");
	}
	
	public static void uploadAudioFile(String filename, String filePathLocation)
			throws S3Exception, AwsServiceException, SdkClientException, IOException {
        S3Client client = S3Client.builder().build();
         
        PutObjectRequest request = PutObjectRequest.builder()
                            .bucket(BUCKET).key(filename).acl("public-read").build();
         
        client.putObject(request, RequestBody.fromFile(new File(filePathLocation)));
        
	}
	 

	public static void deleteFile(String filename) {
		S3Client client = S3Client.builder().build();
		System.out.println("Filename:"+filename);
		DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(BUCKET).key(filename).build();

		client.deleteObject(request);

	}
	
	public static void updateFile(String filename,InputStream inputStream) throws S3Exception, AwsServiceException, SdkClientException, IOException {
		deleteFile(filename);
		uploadFile(filename,inputStream);
		
	}

	public static LocalDateTime getLastModifiedDate(String filename) {
		System.out.println("Inside glmd");
		

		S3Client client = S3Client.builder().build();
		LocalDateTime ldt = null;

		ListObjectsRequest request = ListObjectsRequest.builder().bucket(BUCKET).build();

		ListObjectsResponse response = client.listObjects(request);
		List<S3Object> objects = response.contents();
		
		for(S3Object object:objects) {
			if(object.key().equals(filename)) {
				Instant lastModifiedDate =object.lastModified();
			
				ldt = LocalDateTime.ofInstant(lastModifiedDate, ZoneOffset.UTC);
				System.out.println("Ildt:{}"+ldt);
			}
		}
		
		return ldt;
	}
	
	public static String createObjectUrl(String filename) {
		String objectURL = MessageFormat.format("https://{0}.s3.{1}.amazonaws.com/{2}",BUCKET,REGION,filename);
		return objectURL;
		
	}
	
}
