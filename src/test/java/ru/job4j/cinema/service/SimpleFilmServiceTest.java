package ru.job4j.cinema.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.dto.FilmPreview;
import ru.job4j.cinema.repository.Sql2oFilmRepository;
import ru.job4j.cinema.repository.Sql2oGenreRepository;

import java.util.List;
import java.util.Properties;

/**
 * Тесты для слоя: Сервис
 * Модель: Film, FilmPreview
 */
class SimpleFilmServiceTest {

    private static Sql2oFilmRepository sql2oFilmRepository;

    private static SimpleFilmService simpleFilmService;

    /**
     * Инициализация полей сервиса sql2oFilmRepository, simpleFilmService
     * @throws Exception
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = SimpleFilmServiceTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        Sql2oGenreRepository sql2ogenreRepository = new Sql2oGenreRepository(sql2o);
        simpleFilmService = new SimpleFilmService(sql2oFilmRepository, sql2ogenreRepository);
    }

    /**
     * Получение объекта Film по id существующего фильма
     * Метод Film findById(int id)
     */
    @Test
    public void whenFindByIdThenGetFilm() {
        Film expectedFilm = new Film(1, "Зеленая миля",
                "Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора», каждый из узников "
                        + "которого однажды проходит «зеленую милю» по пути к месту казни. Пол повидал много "
                        + "заключённых и надзирателей за время работы. Однако гигант Джон Коффи, обвинённый в "
                        + "страшном преступлении, стал одним из самых необычных обитателей блока.",
                new Film.Detail(1999, 2, 16, 189), 1);
        Film actualFilm = simpleFilmService.findById(expectedFilm.getId());
        Assertions.assertThat(actualFilm).usingRecursiveComparison().isEqualTo(expectedFilm);
    }

    /**
     * Получение объекта Film по id отсутствующего фильма
     * Метод Film findById(int id)
     */
    @Test
    public void whenFindByIdThenGetSomeFilm() {
        Film actualFilm = sql2oFilmRepository.findById(100);
        Assertions.assertThat(actualFilm).isNull();
    }

    /**
     * Получение полного списка фильмов FilmPreview
     * Метод Collection<FilmPreview> findAll()
     */
    @Test
    public void whenFindAllThenGetAllFilms() {
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
        var actualFilmPreview = simpleFilmService.findAll();
        Assertions.assertThat(actualFilmPreview).usingRecursiveComparison().isEqualTo(expectedFilmPreview);
    }
}