package com.springboot.application.orthoapp.demo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/management")

public class ManagementController {

	@GetMapping
	  public String get() {
	    return "GET:: Management Controller";
	  }
	  
	  @PostMapping
	  public String post() {
	    return "POST:: Management Controller";
	  }
	  
	  @PutMapping
	  public String put() {
	    return "PUT:: Management Controller";
	  }
	  
	  @DeleteMapping
	  public String delete() {
	    return "DELETE:: Management Controller";
	  }

}
