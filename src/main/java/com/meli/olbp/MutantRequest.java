package com.meli.olbp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MutantRequest {
	@NotNull
	@Size(min = 2)
	private String[] dna;

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}
}
