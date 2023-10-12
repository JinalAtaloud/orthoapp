package com.springboot.application.orthoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.application.orthoapp.model.Images;
import com.springboot.application.orthoapp.model.Videos;

import jakarta.transaction.Transactional;

@Repository
public interface ImageRepository extends JpaRepository<Images, Integer> {

	//Videos findByFileName(String fileName);

}
