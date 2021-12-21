package br.com.coodesh.sfn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class ChallengeController {
	
	public ResponseEntity<Void> challenge() {
		return ResponseEntity.ok().build();
	}

}
