package com.example.netflix.services;

import com.example.netflix.models.Favorite;
import com.example.netflix.models.Movie;
import com.example.netflix.repositories.FavoritesRepository;
import com.example.netflix.repositories.UserRepository;

import java.util.List;

public class FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;

    public FavoritesService(FavoritesRepository favoritesRepository, UserRepository userRepository) {
        this.favoritesRepository = favoritesRepository;
        this.userRepository = userRepository;
    }

    public void addFavorite(String email, int movieId) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }

        int userId = userRepository.getUserIdByEmail(email);

        if (userId <= 0 || movieId <= 0) {
            throw new IllegalArgumentException("Id must be greater than zero");
        }

        Favorite favorite = new Favorite(userId, movieId);
        favoritesRepository.addFavorite(favorite);
    }

    public List<Movie> getFavoritesByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }

        List<Movie> favorites = favoritesRepository.readAllFavorites(email);

        if (favorites.isEmpty()) {
            throw new IllegalArgumentException("No favorites found for user: " + email);
        }

        int userId = userRepository.getUserIdByEmail(email);

        if (userId <= 0) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }

        return favorites;
    }

    public void deleteFavorite(String email, int movieId) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }

        int favId = favoritesRepository.getFavoriteIdByEmailAndMovieId(email, movieId);


        if (movieId <= 0 || favId <= 0) {
            throw new IllegalArgumentException("Favorite not found");
        }

        boolean deleted = favoritesRepository.removeFavorite(favId);

        if (!deleted) {
            throw new IllegalArgumentException("Favorite with id " + favId + " not found");
        }
    }
}
