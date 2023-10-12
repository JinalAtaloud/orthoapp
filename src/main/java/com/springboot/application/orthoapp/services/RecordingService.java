package com.springboot.application.orthoapp.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.springboot.application.orthoapp.model.Images;
import com.springboot.application.orthoapp.model.Languages;
import com.springboot.application.orthoapp.s3util.S3Util;

@Service
public class RecordingService {
	private AudioInputStream audioStream;
	private TargetDataLine targetDataLine;

	public synchronized String startRecording(Languages language, Images image) throws Exception {
		String filename="Recording_"+generateUniqueFileName()+".wav";
		String filePathLocation= "Files-Upload/"+filename;
		try {
			System.out.println("Inside isRecording");
			
			AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("Line not supported");
				return null;
			}
			targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			targetDataLine.open(audioFormat);
			targetDataLine.start();
			Thread recordingThread = new Thread(() -> {
				audioStream = new AudioInputStream(targetDataLine);
				try {
					AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, new File(filePathLocation));
					S3Util.uploadAudioFile(filename,filePathLocation);
				}catch(Exception e) {
//					/throw new Exception(e);
				}
			});
			recordingThread.start();

		} catch (Exception e) {
			throw new Exception(e);
		}
		return filename;
	}

	public synchronized void stopRecording() throws Exception {
		if(targetDataLine != null && targetDataLine.isOpen()) {
			targetDataLine.stop();
			targetDataLine.close();
		}

	}
	
	public String generateUniqueFileName() {
	    String filename = "";
	    long millis = System.currentTimeMillis();
	    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	    Calendar c = Calendar.getInstance(); 
	    String datetime = df.format(c.getTime()) + " GMT";
	    datetime = datetime.replace(" ", "");
	    datetime = datetime.replace(":", "");
	    String rndchars = RandomStringUtils.randomAlphanumeric(16);
	    filename = rndchars + "_" + datetime + "_" + millis;
	    return filename;
	}

}
