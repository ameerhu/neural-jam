package com.neural.jam.endpoints.service.impl;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.neural.jam.endpoints.datamodel.Joke;
import com.neural.jam.endpoints.datamodel.repo.JokeRepo;
import com.neural.jam.endpoints.domain.dto.JokeDTOInt;
import com.neural.jam.endpoints.service.IJokeService;
import com.neural.jam.endpoints.shared.exceptions.ValidationException;

@Service
public class JokeService implements IJokeService {

	@Autowired
	private JokeRepo jokeRepo;
	
	@Override
	public Page<Joke> getJokes(Pageable pageable) {
		return jokeRepo.findAll(pageable);
	}

	@Override
	public Page<Joke> getJokes(String term, Pageable pageable) {
		return jokeRepo.findByJokeLike(term, pageable);
	}

	@Override
	public void createJoke(JokeDTOInt jokeDtoInt) {
		Joke joke = mapJokeDTOIntoJoke(jokeDtoInt);
		jokeRepo.save(joke);
	}

	private Joke mapJokeDTOIntoJoke(JokeDTOInt jokeDtoInt) {
		Joke joke = new Joke();
		joke.setJoke(jokeDtoInt.getJoke());
		return joke;
	}

	@Override
	public void updateJoke(String id, JokeDTOInt jokeDtoInt) {
		
		if(!Objects.equals(id, jokeDtoInt.getId()))
			throw new ValidationException("Entity not Valid");
		
		Joke joke = jokeRepo.findById(id).get();
		
		if(joke == null)
			throw new EntityNotFoundException("Entity Does't exist");
		
		joke.setJoke(jokeDtoInt.getJoke());
		
		jokeRepo.save(joke);
		
	}

	@Override
	public void deleteJoke(String id) {
		Joke joke = jokeRepo.findById(id).get();
		
		if(joke == null)
			throw new EntityNotFoundException("Entity Does't exist");
		
		jokeRepo.delete(joke);
	}

	@Override
	public Joke getJokeById(String id) {
		return jokeRepo.findById(id).get();
	}

	@Override
	public Joke randomJoke() {
		return jokeRepo.findRandomJoke();
	}
	
	
}
