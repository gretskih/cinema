package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.preview.SessionPreview;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.FilmSessionService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmSessionControllerTest {

    private FilmSessionService filmSessionService;

    private FilmSessionController filmSessionController;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        filmSessionController = new FilmSessionController(filmSessionService);
    }

    @Test
    public void whenRequestGetAllThenGetPageWithFilmSessionPreviews() {
        var expectedSessionPreviews = List.of(
                new SessionPreview(1, "Зеленая миля", "VIP", LocalDateTime.of(2023, 11, 19, 10, 0, 0), 200),
                new SessionPreview(2, "Побег из Шоушенка", "VIP", LocalDateTime.of(2023, 11, 19, 13, 30, 0), 200),
                new SessionPreview(3, "Форрест Гамп", "VIP", LocalDateTime.of(2023, 11, 19, 16, 30, 0), 200),
                new SessionPreview(4, "Интерстеллар", "VIP", LocalDateTime.of(2023, 11, 19, 19, 30, 0), 200),
                new SessionPreview(5, "Бойцовский клуб", "ATMOS", LocalDateTime.of(2023, 11, 19, 10, 0, 0), 300),
                new SessionPreview(6, "Зеленая миля", "ATMOS", LocalDateTime.of(2023, 11, 19, 13, 0, 0), 300),
                new SessionPreview(7, "Побег из Шоушенка", "ATMOS", LocalDateTime.of(2023, 11, 19, 17, 0, 0), 300),
                new SessionPreview(8, "Форрест Гамп", "ATMOS", LocalDateTime.of(2023, 11, 19, 20, 0, 0), 300),
                new SessionPreview(9, "Интерстеллар", "AURO", LocalDateTime.of(2023, 11, 19, 10, 0, 0), 250),
                new SessionPreview(10, "Бойцовский клуб", "AURO", LocalDateTime.of(2023, 11, 19, 14, 0, 0), 250),
                new SessionPreview(11, "Зеленая миля", "AURO", LocalDateTime.of(2023, 11, 19, 17, 0, 0), 250),
                new SessionPreview(12, "Побег из Шоушенка", "AURO", LocalDateTime.of(2023, 11, 19, 21, 0, 0), 250),
                new SessionPreview(13, "Зеленая миля", "VIP", LocalDateTime.of(2023, 11, 20, 10, 0, 0), 200),
                new SessionPreview(14, "Побег из Шоушенка", "VIP", LocalDateTime.of(2023, 11, 20, 13, 30, 0), 200),
                new SessionPreview(15, "Форрест Гамп", "VIP", LocalDateTime.of(2023, 11, 20, 16, 30, 0), 200),
                new SessionPreview(16, "Интерстеллар", "VIP", LocalDateTime.of(2023, 11, 20, 19, 30, 0), 200),
                new SessionPreview(17, "Бойцовский клуб", "ATMOS", LocalDateTime.of(2023, 11, 20, 10, 0, 0), 300),
                new SessionPreview(18, "Зеленая миля", "ATMOS", LocalDateTime.of(2023, 11, 20, 13, 0, 0), 300),
                new SessionPreview(19, "Побег из Шоушенка", "ATMOS", LocalDateTime.of(2023, 11, 20, 17, 0, 0), 300),
                new SessionPreview(20, "Форрест Гамп", "ATMOS", LocalDateTime.of(2023, 11, 20, 20, 0, 0), 300),
                new SessionPreview(21, "Интерстеллар", "AURO", LocalDateTime.of(2023, 11, 20, 10, 0, 0), 250),
                new SessionPreview(22, "Бойцовский клуб", "AURO", LocalDateTime.of(2023, 11, 20, 14, 0, 0), 250),
                new SessionPreview(23, "Зеленая миля", "AURO", LocalDateTime.of(2023, 11, 20, 17, 0, 0), 250),
                new SessionPreview(24, "Побег из Шоушенка", "AURO", LocalDateTime.of(2023, 11, 20, 21, 0, 0), 250),
                new SessionPreview(25, "Зеленая миля", "VIP", LocalDateTime.of(2023, 11, 21, 10, 0, 0), 200),
                new SessionPreview(26, "Побег из Шоушенка", "VIP", LocalDateTime.of(2023, 11, 21, 13, 30, 0), 200),
                new SessionPreview(27, "Форрест Гамп", "VIP", LocalDateTime.of(2023, 11, 21, 16, 30, 0), 200),
                new SessionPreview(28, "Интерстеллар", "VIP", LocalDateTime.of(2023, 11, 21, 19, 30, 0), 200),
                new SessionPreview(29, "Бойцовский клуб", "ATMOS", LocalDateTime.of(2023, 11, 21, 10, 0, 0), 300),
                new SessionPreview(30, "Зеленая миля", "ATMOS", LocalDateTime.of(2023, 11, 21, 13, 0, 0), 300),
                new SessionPreview(31, "Побег из Шоушенка", "ATMOS", LocalDateTime.of(2023, 11, 21, 17, 0, 0), 300),
                new SessionPreview(32, "Форрест Гамп", "ATMOS", LocalDateTime.of(2023, 11, 21, 20, 0, 0), 300),
                new SessionPreview(33, "Интерстеллар", "AURO", LocalDateTime.of(2023, 11, 21, 10, 0, 0), 250),
                new SessionPreview(34, "Бойцовский клуб", "AURO", LocalDateTime.of(2023, 11, 21, 14, 0, 0), 250),
                new SessionPreview(35, "Зеленая миля", "AURO", LocalDateTime.of(2023, 11, 21, 17, 0, 0), 250),
                new SessionPreview(36, "Побег из Шоушенка", "AURO", LocalDateTime.of(2023, 11, 21, 21, 0, 0), 250),
                new SessionPreview(37, "Зеленая миля", "VIP", LocalDateTime.of(2023, 11, 22, 10, 0, 0), 200),
                new SessionPreview(38, "Побег из Шоушенка", "VIP", LocalDateTime.of(2023, 11, 22, 13, 30, 0), 200),
                new SessionPreview(39, "Форрест Гамп", "VIP", LocalDateTime.of(2023, 11, 22, 16, 30, 0), 200),
                new SessionPreview(40, "Интерстеллар", "VIP", LocalDateTime.of(2023, 11, 22, 19, 30, 0), 200),
                new SessionPreview(41, "Бойцовский клуб", "ATMOS", LocalDateTime.of(2023, 11, 22, 10, 0, 0), 300),
                new SessionPreview(42, "Зеленая миля", "ATMOS", LocalDateTime.of(2023, 11, 22, 13, 0, 0), 300),
                new SessionPreview(43, "Побег из Шоушенка", "ATMOS", LocalDateTime.of(2023, 11, 22, 17, 0, 0), 300),
                new SessionPreview(44, "Форрест Гамп", "ATMOS", LocalDateTime.of(2023, 11, 22, 20, 0, 0), 300),
                new SessionPreview(45, "Интерстеллар", "AURO", LocalDateTime.of(2023, 11, 22, 10, 0, 0), 250),
                new SessionPreview(46, "Бойцовский клуб", "AURO", LocalDateTime.of(2023, 11, 22, 14, 0, 0), 250),
                new SessionPreview(47, "Зеленая миля", "AURO", LocalDateTime.of(2023, 11, 22, 17, 0, 0), 250),
                new SessionPreview(48, "Побег из Шоушенка", "AURO", LocalDateTime.of(2023, 11, 22, 21, 0, 0), 250),
                new SessionPreview(49, "Зеленая миля", "VIP", LocalDateTime.of(2023, 11, 23, 10, 0, 0), 200),
                new SessionPreview(50, "Побег из Шоушенка", "VIP", LocalDateTime.of(2023, 11, 23, 13, 30, 0), 200),
                new SessionPreview(51, "Форрест Гамп", "VIP", LocalDateTime.of(2023, 11, 23, 16, 30, 0), 200),
                new SessionPreview(52, "Интерстеллар", "VIP", LocalDateTime.of(2023, 11, 23, 19, 30, 0), 200),
                new SessionPreview(53, "Бойцовский клуб", "ATMOS", LocalDateTime.of(2023, 11, 23, 10, 0, 0), 300),
                new SessionPreview(54, "Зеленая миля", "ATMOS", LocalDateTime.of(2023, 11, 23, 13, 0, 0), 300),
                new SessionPreview(55, "Побег из Шоушенка", "ATMOS", LocalDateTime.of(2023, 11, 23, 17, 0, 0), 300),
                new SessionPreview(56, "Форрест Гамп", "ATMOS", LocalDateTime.of(2023, 11, 23, 20, 0, 0), 300),
                new SessionPreview(57, "Интерстеллар", "AURO", LocalDateTime.of(2023, 11, 23, 10, 0, 0), 250),
                new SessionPreview(58, "Бойцовский клуб", "AURO", LocalDateTime.of(2023, 11, 23, 14, 0, 0), 250),
                new SessionPreview(59, "Зеленая миля", "AURO", LocalDateTime.of(2023, 11, 23, 17, 0, 0), 250),
                new SessionPreview(60, "Побег из Шоушенка", "AURO", LocalDateTime.of(2023, 11, 23, 21, 0, 0), 250),
                new SessionPreview(61, "Зеленая миля", "VIP", LocalDateTime.of(2023, 11, 24, 10, 0, 0), 250),
                new SessionPreview(62, "Побег из Шоушенка", "VIP", LocalDateTime.of(2023, 11, 24, 13, 30, 0), 250),
                new SessionPreview(63, "Форрест Гамп", "VIP", LocalDateTime.of(2023, 11, 24, 16, 30, 0), 250),
                new SessionPreview(64, "Интерстеллар", "VIP", LocalDateTime.of(2023, 11, 24, 19, 30, 0), 250),
                new SessionPreview(65, "Бойцовский клуб", "ATMOS", LocalDateTime.of(2023, 11, 24, 10, 0, 0), 350),
                new SessionPreview(66, "Зеленая миля", "ATMOS", LocalDateTime.of(2023, 11, 24, 13, 0, 0), 350),
                new SessionPreview(67, "Побег из Шоушенка", "ATMOS", LocalDateTime.of(2023, 11, 24, 17, 0, 0), 350),
                new SessionPreview(68, "Форрест Гамп", "ATMOS", LocalDateTime.of(2023, 11, 24, 20, 0, 0), 350),
                new SessionPreview(69, "Интерстеллар", "AURO", LocalDateTime.of(2023, 11, 24, 10, 0, 0), 300),
                new SessionPreview(70, "Бойцовский клуб", "AURO", LocalDateTime.of(2023, 11, 24, 14, 0, 0), 300),
                new SessionPreview(71, "Зеленая миля", "AURO", LocalDateTime.of(2023, 11, 24, 17, 0, 0), 300),
                new SessionPreview(72, "Побег из Шоушенка", "AURO", LocalDateTime.of(2023, 11, 24, 21, 0, 0), 300),
                new SessionPreview(73, "Зеленая миля", "VIP", LocalDateTime.of(2023, 11, 25, 10, 0, 0), 250),
                new SessionPreview(74, "Побег из Шоушенка", "VIP", LocalDateTime.of(2023, 11, 25, 13, 30, 0), 250),
                new SessionPreview(75, "Форрест Гамп", "VIP", LocalDateTime.of(2023, 11, 25, 16, 30, 0), 250),
                new SessionPreview(76, "Интерстеллар", "VIP", LocalDateTime.of(2023, 11, 25, 19, 30, 0), 250),
                new SessionPreview(77, "Бойцовский клуб", "ATMOS", LocalDateTime.of(2023, 11, 25, 10, 0, 0), 350),
                new SessionPreview(78, "Зеленая миля", "ATMOS", LocalDateTime.of(2023, 11, 25, 13, 0, 0), 350),
                new SessionPreview(79, "Побег из Шоушенка", "ATMOS", LocalDateTime.of(2023, 11, 25, 17, 0, 0), 350),
                new SessionPreview(80, "Форрест Гамп", "ATMOS", LocalDateTime.of(2023, 11, 25, 20, 0, 0), 350),
                new SessionPreview(81, "Интерстеллар", "AURO", LocalDateTime.of(2023, 11, 25, 10, 0, 0), 300),
                new SessionPreview(82, "Бойцовский клуб", "AURO", LocalDateTime.of(2023, 11, 25, 14, 0, 0), 300),
                new SessionPreview(83, "Зеленая миля", "AURO", LocalDateTime.of(2023, 11, 25, 17, 0, 0), 300),
                new SessionPreview(84, "Побег из Шоушенка", "AURO", LocalDateTime.of(2023, 11, 25, 21, 0, 0), 300)
        );
        when(filmSessionService.findAll()).thenReturn(expectedSessionPreviews);

        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var actualSessionPreviews = model.getAttribute("sessionPreviews");

        assertThat(view).isEqualTo("filmsessions/list");
        assertThat(actualSessionPreviews).isEqualTo(expectedSessionPreviews);
    }
}