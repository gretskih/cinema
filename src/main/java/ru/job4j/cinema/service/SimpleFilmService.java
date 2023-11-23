package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository filmRepository, GenreRepository genreRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Film findById(int id) {
        return filmRepository.findById(id);
    }

    @Override
    public Collection<FilmPreview> findAll() {
        Collection<Film> films = filmRepository.findAll();
        Collection<FilmPreview> filmPreviews = new ArrayList<>();
        for (Film film : films) {
            String genre = genreRepository.findById(film.getGenreId()).getName();
            FilmPreview filmPreview = new FilmPreview(film.getId(), film.getName(), film.getDescription(), new FilmPreview.Detail(film.getYear(),
                    film.getMinimalAge(), film.getDurationInMinutes(), genre), film.getFileId());
            filmPreviews.add(filmPreview);
        }
        return filmPreviews;
    }
}
