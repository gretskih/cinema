package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Hall;

import java.util.Properties;

/**
 * Тесты для слоя: Репозиторий
 * Модель: Hall
 */
class Sql2oHallRepositoryTest {

    private static Sql2oHallRepository sql2oHallRepository;

    /**
     * Инициализация поля репизитория sql2oHallRepository
     * @throws Exception
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    /**
     * Поиск существующего в базе зала по id
     * Метод Hall findById(int id)
     */
    @Test
    public void whenFindByIdThenGetHall() {
        Hall expectedHall = new Hall(1, "VIP", 4, 4, "Cамый камерный и индивидуальный. Здесь всего 16 комфортных кресел-реклайнеров, увеличенное расстояние между креслами и рядами, кнопка вызова официанта, а также профессиональное караоке оборудование — именно поэтому  этот зал чаще остальных востребован для проведения закрытых персональных кинопоказов и проведения частных мероприятий, среди которых дни рождения, помолвки, девичники и др.");
        Hall actualHall = sql2oHallRepository.findById(expectedHall.getId());

        Assertions.assertThat(actualHall).usingRecursiveComparison().isEqualTo(expectedHall);
    }

    /**
     * Поиск отсутствующего в базе зала по id
     * Метод Hall findById(int id)
     */
    @Test
    public void whenFindByIdThenGetSomeHall() {
        Hall actualHall = sql2oHallRepository.findById(100);

        Assertions.assertThat(actualHall).isNull();
    }
}