package com.example.netflix.services;

import com.example.netflix.models.Movie;
import com.example.netflix.repositories.MovieRepository;

import java.util.List;

public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void addMovie(String name, String genre, String length, String description) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (genre == null || genre.isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be null or empty");
        }

        if (length == null || length.isEmpty()) {
            throw new IllegalArgumentException("Length cannot be null or empty");
        }

        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }

        Movie movie =  new Movie(name, genre,length,description);
        movieRepository.addMovie(movie);
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = movieRepository.readAllMovies();

        if (movies.isEmpty()) {
            throw new IllegalArgumentException("No movies found in database");
        }

        return movies;
    }

    public void removeMovie(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than zero");
        }

        boolean deleted = movieRepository.removeMovie(id);

        if (!deleted) {
            throw new IllegalArgumentException("Movie with id " + id + " not found");
        }
    }
}
