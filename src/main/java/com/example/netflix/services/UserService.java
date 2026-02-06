package com.example.netflix.services;

import com.example.netflix.models.User;
import com.example.netflix.repositories.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String name, String email) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        User user =  new User(name, email);
        userRepository.addUser(user);
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.readAllUsers();

        if (users.isEmpty()) {
            throw new IllegalArgumentException("No users found");
        }

        return users;
    }

    public void updateUser(int id, String name, String email) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than zero");
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        User user = new User(id, name, email);
        userRepository.updateUser(user);
    }

    public void deleteUser(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than zero");
        }

        boolean deleted = userRepository.deleteUser(id);

        if (!deleted) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
    }
}
