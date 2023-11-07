package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.preview.SessionPreview;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.util.ArrayList;
import java.util.Collection;

@ThreadSafe
@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;
    private final HallRepository hallRepository;
    private final FilmRepository filmRepository;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository, HallRepository hallRepository, FilmRepository filmRepository) {
        this.filmSessionRepository = filmSessionRepository;
        this.hallRepository = hallRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public FilmSession findById(int id) {
        return filmSessionRepository.findById(id);
    }

    @Override
    public Collection<SessionPreview> findAll() {
        Collection<FilmSession> filmSessions = filmSessionRepository.findAll();
        Collection<SessionPreview> sessionPreviews = new ArrayList<>();
        for (FilmSession filmSession : filmSessions) {
            String hallName = hallRepository.findById(filmSession.getHallId()).getName();
            String filmName = filmRepository.findById(filmSession.getFilmId()).getName();
            SessionPreview sessionPreview = new SessionPreview(filmSession.getId(), filmName, hallName,
                    filmSession.getStartTime(), filmSession.getPrice());
            sessionPreviews.add(sessionPreview);
        }
        return sessionPreviews;
    }
}
