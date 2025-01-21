package com.example.microservice.movieservice.repositories;

import com.example.microservice.movieservice.models.Movie;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface MovieRepository extends MongoRepository<Movie, String> {
    Optional<Movie> findByMovieId(Integer movieId);
}
