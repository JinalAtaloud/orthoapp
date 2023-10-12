package com.springboot.application.orthoapp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.springframework.beans.factory.annotation.Value;

import com.springboot.application.orthoapp.s3util.S3Util;

import jakarta.annotation.Resource;

public class RecordingUtil {
	
	@Value("${audio.files.temp.location}") 
	private Resource templateLocation;
	

	static final long RECORD_TIME = 5000;
	File wavFile = new File(templateLocation+"\\RecordAudio.wav");
	
	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	TargetDataLine line;

	public static void saveRecording() {
		  final RecordingUtil recorder = new RecordingUtil();
		  
	        // creates a new thread that waits for a specified
	        // of time before stopping
	        Thread stopper = new Thread(new Runnable() {
	            public void run() {
	                try {
	                    Thread.sleep(RECORD_TIME);
	                } catch (InterruptedException ex) {
	                    ex.printStackTrace();
	                }
	                recorder.finish();
	            }
	        });
	 
	        stopper.start();
	        recorder.start();
	}


	AudioFormat getAudioFormat() {
		float sampleRate = 16000;
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		return format;
	}

	void finish() {
		line.stop();
		line.close();
		System.out.println("Finished");
	}

	void start() {
		try {
			AudioFormat format = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

			// checks if system supports the data line
			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("Line not supported");
				System.exit(0);
			}
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start(); // start capturing
			AudioInputStream ais = new AudioInputStream(line);
			// start recording
			AudioSystem.write(ais, fileType, wavFile);
			//S3Util.uploadAudioFile("RecordAudio.wav");
			

		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
