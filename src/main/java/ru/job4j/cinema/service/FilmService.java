package ru.job4j.cinema.service;

import ru.job4j.cinema.preview.FilmPreview;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmService {
    Film findById(int id);

    Collection<FilmPreview> findAll();
}
