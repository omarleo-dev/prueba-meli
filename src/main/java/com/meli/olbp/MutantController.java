package com.meli.olbp;

import java.util.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MutantController {
	@PostMapping("/mutant")
	public Mono<ResponseEntity> isMutant(@Valid @RequestBody MutantRequest request) {
		return Mono.just(new ResponseEntity<>(
				validateDNA(request.getDna())?
					HttpStatus.OK:
					HttpStatus.FORBIDDEN
				));
	}
	

    /**
     * This Method verify if the String[] have characters allowed and the length be correct
     * @param dna
     * @return true of false
     */
    public boolean validateDNA(String[] dna){
        boolean validate = true;
        int lengthChars = dna.length;
        //
        for (String line: dna) {
            List<String> myCodes = new ArrayList<String>(Arrays.asList(line.split("")));
            long discard = myCodes.stream()
                    .filter(x -> x.matches("[^ATCGatcg]+"))
                    .count();
            if (line.length() != lengthChars || discard > 0){
                validate = false;
                break;
            }                       
        }
        return  validate;
    }
}
