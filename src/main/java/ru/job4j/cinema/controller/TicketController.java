package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

@ThreadSafe
@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final HallService hallService;
    private final FilmSessionService filmSessionService;
    private final TicketService ticketService;

    public TicketController(HallService hallService, FilmSessionService filmSessionService, TicketRepository ticketRepository, TicketService ticketService) {
        this.hallService = hallService;
        this.filmSessionService = filmSessionService;
        this.ticketService = ticketService;
    }

    @GetMapping("/create")
    public String getCreationPage(Model model, int sessionId, String filmName) {

        FilmSession filmSession = filmSessionService.findById(sessionId);
        Hall hall = hallService.findById(filmSession.getHallId());
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("filmName", filmName);
        model.addAttribute("hallName", hall.getName());
        model.addAttribute("startTime", filmSession.getStartTime());
        model.addAttribute("price", filmSession.getPrice());
        model.addAttribute("rows", hallService.getList(hall.getRowCount()));
        model.addAttribute("places", hallService.getList(hall.getPlaceCount()));
        return "tickets/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Ticket ticket, Model model) {
        var newTicket = ticketService.save(ticket);
        if (newTicket.isEmpty()) {
            model.addAttribute("message", "Не удалось приобрести билет на заданное место. "
                    + "Вероятно оно уже занято. Перейдите на страницу бронирования билетов и попробуйте снова.");
            return "errors/404";
        }
        model.addAttribute("message", "Вы успешно приобрели билет: ряд %d место %d"
                .formatted(newTicket.get().getRowNumber(), newTicket.get().getPlaceNumber()));
        return "tickets/success";
    }
}
