package com.neural.jam.endpoints.domain.dto;

import org.springframework.lang.NonNull;

public class JokeDTOInt {

	private String id;
	
	@NonNull
	private String joke;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJoke() {
		return joke;
	}

	public void setJoke(String joke) {
		this.joke = joke;
	}
	
}
