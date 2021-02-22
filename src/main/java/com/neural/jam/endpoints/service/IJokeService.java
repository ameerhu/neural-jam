package com.neural.jam.endpoints.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.neural.jam.endpoints.datamodel.Joke;
import com.neural.jam.endpoints.domain.dto.JokeDTOInt;

public interface IJokeService {
	
	public Page<Joke> getJokes(Pageable pageable);
	
	public Page<Joke> getJokes(String term, Pageable pageable);
	
	public void createJoke(JokeDTOInt jokeDtoInt);

	public void updateJoke(String id, JokeDTOInt jokeDtoInt);
	
	public void deleteJoke(String id);
	
	public Joke getJokeById(String id);
	
	public Joke randomJoke();
}
