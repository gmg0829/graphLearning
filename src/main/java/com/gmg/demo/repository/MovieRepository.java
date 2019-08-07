package com.gmg.demo.repository;

import com.gmg.demo.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    Movie findByTitle(String title);
}