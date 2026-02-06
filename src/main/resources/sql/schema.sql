CREATE DATABASE streamingplatformdb;
USE streamingplatformdb;

CREATE TABLE users (
    id    int PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(50),
    email VARCHAR(100)
);

CREATE TABLE movies (
    id          int PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50),
    genre       VARCHAR(50),
    length      VARCHAR(10),
    description VARCHAR(500)
);

CREATE TABLE favorites (
    fav_id   int PRIMARY KEY AUTO_INCREMENT,
    usr_id   int,
    movie_id int,

    CONSTRAINT fk_user FOREIGN KEY (usr_id) REFERENCES users (id),
    CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movies (id)
);







