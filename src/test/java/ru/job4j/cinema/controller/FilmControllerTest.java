package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.service.FilmService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тесты для слоя: Контроллер
 * RequestMapping("/films")
 */
class FilmControllerTest {

    private FilmService filmService;
    private FilmController filmController;

    /**
     * Инициализация mock заглушками полей filmService, filmController
     */
    @BeforeEach
    public void initServices() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    /**
     * Получение страницы films/list и полного списка фильмов
     * Метод String getAll(Model model)
     */
    @Test
    public void whenRequestGetAllThenGetPageWithFilms() {
        FilmPreview expectedFilmPreview1 = new FilmPreview(1, "Зеленая миля",
                "Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора», каждый из узников "
                        + "которого однажды проходит «зеленую милю» по пути к месту казни. Пол повидал много "
                        + "заключённых и надзирателей за время работы. Однако гигант Джон Коффи, обвинённый в "
                        + "страшном преступлении, стал одним из самых необычных обитателей блока.",
                new FilmPreview.Detail(1999, 16, 189, "Драма"), 1);
        FilmPreview expectedFilmPreview2 = new FilmPreview(2, "Побег из Шоушенка",
                "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись "
                        + "в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими "
                        + "по обе стороны решётки. Каждый, кто попадает в эти стены, становится их рабом до конца "
                        + "жизни. Но Энди, обладающий живым умом и доброй душой, находит подход как к заключённым, "
                        + "так и к охранникам, добиваясь их особого к себе расположения.",
                new FilmPreview.Detail(1994, 16, 142, "Драма"), 2);
        FilmPreview expectedFilmPreview3 = new FilmPreview(3, "Форрест Гамп",
                "Сидя на автобусной остановке, Форрест Гамп — не очень умный, но добрый и открытый парень "
                        + "— рассказывает случайным встречным историю своей необыкновенной жизни. С самого малолетства "
                        + "парень страдал от заболевания ног, соседские мальчишки дразнили его, но в один прекрасный "
                        + "день Форрест открыл в себе невероятные способности к бегу. Подруга детства Дженни всегда "
                        + "его поддерживала и защищала, но вскоре дороги их разошлись.",
                new FilmPreview.Detail(1994, 18, 142, "Комедия"), 3);
        FilmPreview expectedFilmPreview4 = new FilmPreview(4, "Интерстеллар",
                "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному "
                        + "кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая "
                        + "предположительно соединяет области пространства-времени через большое расстояние) в "
                        + "путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и "
                        + "найти планету с подходящими для человечества условиями.",
                new FilmPreview.Detail(2014, 16, 169, "Фантастика"), 4);
        FilmPreview expectedFilmPreview5 = new FilmPreview(5, "Бойцовский клуб",
                "Сотрудник страховой компании страдает хронической бессонницей и отчаянно пытается "
                        + "вырваться из мучительно скучной жизни. Однажды в очередной командировке он встречает "
                        + "некоего Тайлера Дёрдена — харизматического торговца мылом с извращенной философией. "
                        + "Тайлер уверен, что самосовершенствование — удел слабых, а единственное, ради чего стоит "
                        + "жить, — саморазрушение.",
                new FilmPreview.Detail(1999, 18, 139, "Триллер"), 5);

        var expectedFilmPreview = List.of(expectedFilmPreview1, expectedFilmPreview2, expectedFilmPreview3, expectedFilmPreview4, expectedFilmPreview5);
        when(filmService.findAll()).thenReturn(expectedFilmPreview);

        var model = new ConcurrentModel();
        var view = filmController.getAll(model);
        var actualFilmPreview = model.getAttribute("filmPreviews");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualFilmPreview).isEqualTo(expectedFilmPreview);
    }
}