package com.springboot.application.orthoapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.springboot.application.orthoapp.model.Recording;

@Repository
public interface RecordingRepository extends MongoRepository<Recording, String> {

}
