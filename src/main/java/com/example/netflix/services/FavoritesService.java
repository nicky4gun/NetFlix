package com.example.netflix.services;

import com.example.netflix.models.Favorite;
import com.example.netflix.models.Movie;
import com.example.netflix.repositories.FavoritesRepository;

import java.util.List;

public class FavoritesService {
    private final FavoritesRepository favoritesRepository;

    public FavoritesService(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    public void addFavorite(String email, int movieId) {
        if (email == null || email.isEmpty()) {
            throw  new  IllegalArgumentException("Email is null or empty");
        }

        int userId = favoritesRepository.getUserIdByEmail(email);

        if (userId <= 0 || movieId <= 0) {
            throw new IllegalArgumentException("Id must be greater than zero");
        }

        Favorite favorite = new Favorite(userId, movieId);
        favoritesRepository.addFavorite(favorite);
    }

    public List<Movie> getFavoritesByEmail(String email) {
        return favoritesRepository.readAllFavorites(email);
    }

    public void deleteFavorite(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than zero");
        }

        boolean deleted = favoritesRepository.removeFavorite(id);

        if (!deleted) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
    }
}
