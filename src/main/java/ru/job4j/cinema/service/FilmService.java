package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.model.Film;

import java.util.Collection;

public interface FilmService {
    Film findById(int id);

    Collection<FilmPreview> findAll();
}
