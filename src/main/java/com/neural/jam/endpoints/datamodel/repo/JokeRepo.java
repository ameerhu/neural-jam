package com.neural.jam.endpoints.datamodel.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.neural.jam.endpoints.datamodel.Joke;

public interface JokeRepo extends PagingAndSortingRepository<Joke, String>{

	@Query(" select j from Joke j where j.joke like (%:joke%)")
	Page<Joke> findByJokeLike(@Param("joke") String joke, Pageable pageable);
	
	@Query(nativeQuery = true, value = "SELECT j.* FROM Joke j ORDER BY RAND() LIMIT 1" )
	Joke findRandomJoke();
}
