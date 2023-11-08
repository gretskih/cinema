package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @Test
    public void whenFindByIdThenGetFilm() {
        Film expectedFilm = new Film(1, "Зеленая миля",
                "Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора», каждый из узников "
                        + "которого однажды проходит «зеленую милю» по пути к месту казни. Пол повидал много "
                        + "заключённых и надзирателей за время работы. Однако гигант Джон Коффи, обвинённый в "
                        + "страшном преступлении, стал одним из самых необычных обитателей блока.",
                new Film.Detail(1999, 2, 16, 189), 1);
        Film actualFilm = sql2oFilmRepository.findById(expectedFilm.getId());
        Assertions.assertThat(actualFilm).usingRecursiveComparison().isEqualTo(expectedFilm);
    }

    @Test
    public void whenFindByIdThenGetSomeFilm() {
        Film expectedFilm = new Film(1, "Зеленая миля",
                "Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора», каждый из узников "
                        + "которого однажды проходит «зеленую милю» по пути к месту казни. Пол повидал много "
                        + "заключённых и надзирателей за время работы. Однако гигант Джон Коффи, обвинённый в "
                        + "страшном преступлении, стал одним из самых необычных обитателей блока.",
                new Film.Detail(1999, 2, 16, 189), 1);
        Film actualFilm = sql2oFilmRepository.findById(expectedFilm.getId() + 1);
        Assertions.assertThat(actualFilm).usingRecursiveComparison().isNotEqualTo(expectedFilm);
    }

    @Test
    public void whenFindAllThenGetAllFilms() {
        Film expectedFilm1 = new Film(1, "Зеленая миля",
                "Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора», каждый из узников "
                        + "которого однажды проходит «зеленую милю» по пути к месту казни. Пол повидал много "
                        + "заключённых и надзирателей за время работы. Однако гигант Джон Коффи, обвинённый в "
                        + "страшном преступлении, стал одним из самых необычных обитателей блока.",
                new Film.Detail(1999, 2, 16, 189), 1);
        Film expectedFilm2 = new Film(2, "Побег из Шоушенка",
                "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись "
                        + "в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими "
                        + "по обе стороны решётки. Каждый, кто попадает в эти стены, становится их рабом до конца "
                        + "жизни. Но Энди, обладающий живым умом и доброй душой, находит подход как к заключённым, "
                        + "так и к охранникам, добиваясь их особого к себе расположения.",
                new Film.Detail(1994, 2, 16, 142), 2);
        Film expectedFilm3 = new Film(3, "Форрест Гамп",
                "Сидя на автобусной остановке, Форрест Гамп — не очень умный, но добрый и открытый парень "
                        + "— рассказывает случайным встречным историю своей необыкновенной жизни. С самого малолетства "
                        + "парень страдал от заболевания ног, соседские мальчишки дразнили его, но в один прекрасный "
                        + "день Форрест открыл в себе невероятные способности к бегу. Подруга детства Дженни всегда "
                        + "его поддерживала и защищала, но вскоре дороги их разошлись.",
                new Film.Detail(1994, 6, 18, 142), 3);
        Film expectedFilm4 = new Film(4, "Интерстеллар",
                "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному "
                        + "кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая "
                        + "предположительно соединяет области пространства-времени через большое расстояние) в "
                        + "путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и "
                        + "найти планету с подходящими для человечества условиями.",
                new Film.Detail(2014, 4, 16, 169), 4);
        Film expectedFilm5 = new Film(5, "Бойцовский клуб",
                "Сотрудник страховой компании страдает хронической бессонницей и отчаянно пытается "
                        + "вырваться из мучительно скучной жизни. Однажды в очередной командировке он встречает "
                        + "некоего Тайлера Дёрдена — харизматического торговца мылом с извращенной философией. "
                        + "Тайлер уверен, что самосовершенствование — удел слабых, а единственное, ради чего стоит "
                        + "жить, — саморазрушение.",
                new Film.Detail(1999, 1, 18, 139), 5);

        Collection<Film> expectedFilms = Arrays.asList(expectedFilm1, expectedFilm2, expectedFilm3, expectedFilm4, expectedFilm5);
        Collection<Film> actualFilms = sql2oFilmRepository.findAll();
        Assertions.assertThat(actualFilms).usingRecursiveComparison().isEqualTo(expectedFilms);
    }
}