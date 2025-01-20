package com.example.microservice.movieservice.services;

import com.example.microservice.movieservice.models.Movie;
import com.example.microservice.movieservice.repositories.MovieRepository;
import com.example.microservice.share.services.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService extends BaseServiceImpl<Movie, String> {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository repository) {
        super(repository);
        this.movieRepository = repository;
    }

    public Movie updateMovie(String id, Movie movie) {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        if (movieOptional.isPresent()) {
            Movie existingMovie = movieOptional.get();

            if (movie.getMovieId() != null) {
                existingMovie.setMovieId(movie.getMovieId());
            }
            if (movie.getTitle() != null) {
                existingMovie.setTitle(movie.getTitle());
            }
            if (movie.getGenres() != null) {
                existingMovie.setGenres(movie.getGenres());
            }

            return movieRepository.save(existingMovie);
        } else {
            throw new RuntimeException("Movie not found with id: " + id);
        }
    }

    public Movie save(Movie movie) {
        if (movieRepository.findByMovieId(movie.getMovieId()).isPresent()) {
            throw new RuntimeException("Movie with movieId " + movie.getMovieId() + " already exists.");
        }

        return movieRepository.save(movie);
    }
}
