package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {

    private HallService hallService;
    private FilmSessionService filmSessionService;
    private TicketService ticketService;
    private TicketController ticketController;

    @BeforeEach
    public void initServices() {
        hallService = mock(HallService.class);
        filmSessionService = mock(FilmSessionService.class);
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(hallService, filmSessionService, ticketService);
    }

    @Test
    public void whenRequestGetCreationPageThenGetCreateNewTicketPage() {
        int sessionId = 1;
        var sessionIdCaptor = ArgumentCaptor.forClass(int.class);
        String filmName = "Зеленая миля";
        FilmSession filmSession = new FilmSession(1, 1, 1, LocalDateTime.of(2023, 11, 19, 10, 0, 0), LocalDateTime.of(2023, 11, 19, 13, 9, 0), 200);
        int hallId = 1;
        var hallIdCaptor = ArgumentCaptor.forClass(int.class);
        Hall hall = new Hall(1, "VIP", 4, 4, "Cамый камерный и индивидуальный. Здесь всего 16 комфортных кресел-реклайнеров, увеличенное расстояние между креслами и рядами, кнопка вызова официанта, а также профессиональное караоке оборудование — именно поэтому  этот зал чаще остальных востребован для проведения закрытых персональных кинопоказов и проведения частных мероприятий, среди которых дни рождения, помолвки, девичники и др.");
        int rowPlaceCount = 4;
        var expectedList = IntStream.range(1, rowPlaceCount + 1).boxed().collect(Collectors.toList());

        when(filmSessionService.findById(sessionIdCaptor.capture())).thenReturn(filmSession);
        when(hallService.findById(hallIdCaptor.capture())).thenReturn(hall);

        var model = new ConcurrentModel();
        var view = ticketController.getCreationPage(model, sessionId, filmName);
        var actualFilmName = model.getAttribute("filmName");
        var actualHall = model.getAttribute("hall");
        var actualFilmSession = model.getAttribute("filmSession");
        var actualRows = model.getAttribute("rows");
        var actualPlaces = model.getAttribute("places");
        var actualSessionIdFromCapture = sessionIdCaptor.getValue();
        var actualHallId = hallIdCaptor.getValue();

        assertThat(actualFilmSession).usingRecursiveComparison().isEqualTo(filmSession);
        assertThat(hall).usingRecursiveComparison().isEqualTo(hall);
        assertThat(actualFilmName).isEqualTo(filmName);
        assertThat(actualRows).isEqualTo(expectedList);
        assertThat(actualPlaces).isEqualTo(expectedList);
        assertThat(view).isEqualTo("tickets/create");
        assertThat(actualSessionIdFromCapture).isEqualTo(sessionId);
        assertThat(actualHallId).isEqualTo(hallId);
    }

    @Test
    public void whenPostTicketThenGetPageSuccessWithMessage() {
        Ticket ticket = new Ticket(1, 1, 1, 1, 1);
        var ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        String expectedMsg = "Вы успешно приобрели билет: ряд %d место %d"
                .formatted(ticket.getRowNumber(), ticket.getPlaceNumber());
        when(ticketService.save(ticketCaptor.capture())).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var view = ticketController.create(ticket, model);
        var actualMessage = model.getAttribute("message");
        var actualTicket = ticketCaptor.getValue();

        assertThat(view).isEqualTo("tickets/success");
        assertThat(actualMessage).isEqualTo(expectedMsg);
        assertThat(actualTicket).isEqualTo(ticket);
    }

    @Test
    public void whenPostTicketThenGetErrorPageWithErrorMessage() {
        Ticket ticket = new Ticket(1, 1, 1, 1, 1);
        var ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        String expectedMsg = "Не удалось приобрести билет на заданное место. "
                + "Вероятно оно уже занято. Перейдите на страницу бронирования билетов и попробуйте снова.";
        when(ticketService.save(ticketCaptor.capture())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = ticketController.create(ticket, model);
        var actualMessage = model.getAttribute("message");
        var actualTicket = ticketCaptor.getValue();

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectedMsg);
        assertThat(actualTicket).isEqualTo(ticket);
    }
}