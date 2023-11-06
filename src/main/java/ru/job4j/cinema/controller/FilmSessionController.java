package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.SimpleFilmSessionService;

@ThreadSafe
@Controller
@RequestMapping("/filmsessions")
public class FilmSessionController {

    private final SimpleFilmSessionService simpleFilmSessionService;

    public FilmSessionController(SimpleFilmSessionService simpleFilmSessionService) {
        this.simpleFilmSessionService = simpleFilmSessionService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("sessionPreviews", simpleFilmSessionService.findAll());
        return "filmsessions/list";
    }

}
