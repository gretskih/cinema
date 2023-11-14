package ru.job4j.cinema.service;

import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.preview.FilmPreview;
import ru.job4j.cinema.preview.SessionPreview;

import java.util.Collection;
import java.util.Map;

public interface FilmSessionService {
    FilmSession findById(int id);

    Map<String, Collection<SessionPreview>> findAll();
}
