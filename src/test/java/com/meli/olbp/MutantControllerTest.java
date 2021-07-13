package com.meli.olbp;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = MutantController.class)

public class MutantControllerTest {

	@Autowired
    private WebTestClient webTestClient;
	
	@MockBean
	MutantService mutantService;
	
	@Test
	public void testMutantOK() {
		when(mutantService.isMutant(new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"}))
		.thenReturn(Mono.just(true));
		webTestClient.post()
		.uri("/mutant")
		.contentType(MediaType.APPLICATION_JSON)
		.syncBody("{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}")
		.exchange().expectStatus().isOk();
	}
	
	@Test
	public void testMutantBadRequest() {
		webTestClient.post()
		.uri("/mutant")
		.contentType(MediaType.APPLICATION_JSON)
		.syncBody("{\"dna\":1}")
		.exchange().expectStatus().isBadRequest();
	}
	
	@Test
	public void testMutantNullRequest() {
		webTestClient.post()
		.uri("/mutant")
		.contentType(MediaType.APPLICATION_JSON)
		.syncBody("{}")
		.exchange().expectStatus().isBadRequest();
	}
	
	@Test
	public void testMutantForbidden() {
		when(mutantService.isMutant(new String[]{"ATGZGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"}))
		.thenReturn(Mono.just(false));
		webTestClient.post()
		.uri("/mutant")
		.contentType(MediaType.APPLICATION_JSON)
		.syncBody("{\"dna\":[\"ATGZGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}")
		.exchange().expectStatus().isForbidden();
	}

}
