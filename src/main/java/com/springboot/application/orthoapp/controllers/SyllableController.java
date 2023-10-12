package com.springboot.application.orthoapp.controllers;

import static com.springboot.application.orthoapp.model.SyllableType.BI;
import static com.springboot.application.orthoapp.model.SyllableType.DEFAULT;
import static com.springboot.application.orthoapp.model.SyllableType.MONO;
import static com.springboot.application.orthoapp.model.SyllableType.POLY;
import static com.springboot.application.orthoapp.model.SyllableType.TRI;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.application.orthoapp.dto.SyllableDto;
import com.springboot.application.orthoapp.exceptions.DataNotFoundException;
import com.springboot.application.orthoapp.model.Images;
import com.springboot.application.orthoapp.model.Syllable;
import com.springboot.application.orthoapp.model.SyllableType;
import com.springboot.application.orthoapp.repository.SyllableRepository;
import com.springboot.application.orthoapp.services.OrthoappImageService;
import com.springboot.application.orthoapp.services.OrthoappSyllableService;
import com.springboot.application.orthoapp.services.OrthoappUserService;
import com.springboot.application.orthoapp.user.User;

@RestController
@RequestMapping("api/v1/syllable")
public class SyllableController {
	private int taps =0;
	private SyllableRepository syllableRepository;
	private OrthoappImageService orthoappImageService;
	private OrthoappUserService userService;
	private OrthoappSyllableService syllableService;
	

	public SyllableController(SyllableRepository syllableRepository,
			OrthoappImageService orthoappImageService, OrthoappUserService userService,OrthoappSyllableService syllableService) {
		this.syllableRepository = syllableRepository;
		this.orthoappImageService = orthoappImageService;
		this.userService = userService;
		this.syllableService=syllableService;
	}

	@PostMapping("/countSyllable") public ResponseEntity<SyllableType> countSyllable(@RequestBody Map<String, Object> requestMap) throws Exception{
		Syllable syllable = new Syllable();
		Integer image_id=(Integer)requestMap.get("image_id");
		Integer user_id = (Integer)requestMap.get("user_id");
		int tapCount = (int) requestMap.get("tap_count");
		
		if(image_id==null) {
			throw new DataNotFoundException("Image id can't be null");
		}
		
		Images image = orthoappImageService.getImageById(image_id);
		if(image==null) {
			throw new DataNotFoundException("Image id does not exists");
		}
		
		User user=userService.getId(user_id);
		if(user==null) {
			throw new DataNotFoundException("user id does not exists");
		}
		
		SyllableType syllabicType ;
        switch (tapCount) {
        case 1:
            syllabicType = MONO;
            break;
        case 2:
            syllabicType = BI;
            break;
        case 3:
            syllabicType = TRI;
            break;
        case 4:
            syllabicType = POLY;
            break;
        default:
            syllabicType = DEFAULT;
            break;
    }
        syllable.setSyallableType(syllabicType);
        syllable.setImage(image);
        syllable.setUser(user);
        syllableRepository.save(syllable);
        return new ResponseEntity<>(syllabicType, HttpStatus.OK);
		
	}

//	@GetMapping("get/{syllableType}") public List<Syllable> getBySyllableType(@PathVariable SyllableType syllableType){
//		List<Syllable> syllables = syllableRepository.findBySyallableType(syllableType);
//		System.out.println("syllables:{}"+syllables);
//		return syllables;
//	}
	
	@GetMapping("/syllables") public List<SyllableDto> getAllSyllables(){
		return syllableService.getAllSyllables();
	}

	
	@RequestMapping(value ="/get/{syllableType}", method= RequestMethod.GET)
	public List<SyllableDto> getSyllablesByType(@PathVariable("syllableType") SyllableType syllableType){
		return syllableService.getSyllablesByType(syllableType);
	}

}
