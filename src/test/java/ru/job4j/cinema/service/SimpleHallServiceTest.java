package ru.job4j.cinema.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.Sql2oHallRepository;

import java.util.Properties;

/**
 * Тесты для слоя: Сервис
 * Модель: Hall
 */
class SimpleHallServiceTest {

    private static Sql2oHallRepository  sql2oHallRepository;

    private static SimpleHallService simpleHallService;

    /**
     * Инициализация полей сервиса sql2oHallRepository, simpleHallService
     * @throws Exception
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = SimpleHallServiceTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oHallRepository = new Sql2oHallRepository(sql2o);
        simpleHallService = new SimpleHallService(sql2oHallRepository);
    }

    /**
     * Получение объекта Hall по id существующего зала
     * Метод Hall findById(int id)
     */
    @Test
    public void whenFindByIdThenGetHall() {
        Hall expectedHall = new Hall(1, "VIP", 4, 4, "Cамый камерный и индивидуальный. Здесь всего 16 комфортных кресел-реклайнеров, увеличенное расстояние между креслами и рядами, кнопка вызова официанта, а также профессиональное караоке оборудование — именно поэтому  этот зал чаще остальных востребован для проведения закрытых персональных кинопоказов и проведения частных мероприятий, среди которых дни рождения, помолвки, девичники и др.");
        Hall actualHall = simpleHallService.findById(expectedHall.getId());

        Assertions.assertThat(actualHall).usingRecursiveComparison().isEqualTo(expectedHall);
    }

    /**
     * Получение объекта Hall по id отсутствующего зала
     * Метод Hall findById(int id)
     */
    @Test
    public void whenFindByIdThenGetSomeHall() {
        Hall actualHall = simpleHallService.findById(1000);

        Assertions.assertThat(actualHall).isNull();
    }
}