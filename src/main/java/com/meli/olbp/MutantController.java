package com.meli.olbp;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class MutantController {

	@Autowired
	private MutantService mutantService;

	@PostMapping("/mutant")
	public Mono<ResponseEntity> isMutant(@Valid @RequestBody MutantRequest request) {
		return mutantService.isMutant(request.getDna()).map(e -> e?ResponseEntity.ok().build():ResponseEntity.status(HttpStatus.FORBIDDEN).build());
	}

}
