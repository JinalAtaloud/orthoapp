package com.springboot.application.orthoapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.application.orthoapp.model.Videos;
import com.springboot.application.orthoapp.repository.SyllableRepository;
import com.springboot.application.orthoapp.repository.TrainingVideosRepository;
import com.springboot.application.orthoapp.user.User;
import com.springboot.application.orthoapp.user.UserRepository;

@Service
public class OrthoappUserService {
	private UserRepository userRepository;
	
	

	public OrthoappUserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}



	public User getId(Integer user_id) {
		return userRepository.findById(user_id).isPresent() ? userRepository.findById(user_id).get(): null;
	}	
}
