package com.neural.jam.endpoints.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neural.jam.endpoints.datamodel.Joke;
import com.neural.jam.endpoints.domain.dto.JokeDTOInt;
import com.neural.jam.endpoints.service.IJokeService;
import com.neural.jam.endpoints.shared.exceptions.ValidationException;

@RestController
@RequestMapping("/jokes")
public class JokeController {

	@Autowired
	private IJokeService jokeService;
	
	@GetMapping()
	public Page<Joke> getJokes(
			@RequestParam(name = "page", required = false) String page,
			@RequestParam(name = "size", required = false) String size,
			@RequestParam(name = "search", required = false) String search){
		
		PageRequest pageRequest = PageRequest.of(
				page == null ? 0 : Integer.parseInt(page), 
				size == null ? 0 : Integer.parseInt(size));
		
		if(search == null)
			return jokeService.getJokes(pageRequest);
		else
			return jokeService.getJokes(search, pageRequest);
	}
	
	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createJoke(@RequestBody JokeDTOInt jokeDtoInt, BindingResult result) {
		 if(result.hasErrors())
			 throw new ValidationException("Invalid Input");
		 
		 jokeService.createJoke(jokeDtoInt);
	}
	
	@PostMapping("{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateJoke(@PathVariable("id") String id, @RequestBody JokeDTOInt jokeDtoInt) {
		jokeService.updateJoke(id, jokeDtoInt);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteJoke(@PathVariable("id") String id) {
		jokeService.deleteJoke(id);
	}

	@GetMapping("{id}")
	public Joke getById(@PathVariable("id") String id) {
		return jokeService.getJokeById(id);
	}
	
	@GetMapping("/random")
	public Joke getById() {
		return jokeService.randomJoke();
	}
	
}
