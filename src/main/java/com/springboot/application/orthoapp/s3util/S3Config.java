package com.springboot.application.orthoapp.s3util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
	private final String awsAccessKeyId = "AKIAUTDCCVOWFEGPJ66X";
	private final String awsSecretAccessKey = "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";
	private final Region region = Region.AP_SOUTH_1;
	
	@Bean
	public S3Client s3Client() {
		AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey);
		return S3Client.builder()
				.region(region)
				.credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
				.build();
	}

}
