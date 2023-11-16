package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.FilmSession;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * Тесты для слоя: Репозиторий
 * Модель: FilmSession
 */
class Sql2oFilmSessionRepositoryTest {

    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    /**
     * Инициализация поля репизитория sql2oFilmSessionRepository
     * @throws Exception
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    /**
     * Поиск существующей в базе сессии по id
     * Метод FilmSession findById(int id)
     */
    @Test
    public void whenFindByIdThenGetFilmSession() {
        FilmSession expectedFilmSession = new FilmSession(12, 2, 3,
                LocalDateTime.of(2023, 11, 19, 21, 0, 0),
                LocalDateTime.of(2023, 11, 19, 23, 22, 0), 250);
        FilmSession actualFilmSession = sql2oFilmSessionRepository.findById(expectedFilmSession.getId());
        Assertions.assertThat(actualFilmSession).usingRecursiveComparison().isEqualTo(expectedFilmSession);
    }

    /**
     * Поиск отсутствующей в базе сессии по id
     * Метод FilmSession findById(int id)
     */
    @Test
    public void whenFindByIdThenGetSomeFilmSession() {
        FilmSession actualFilmSession = sql2oFilmSessionRepository.findById(100);
        Assertions.assertThat(actualFilmSession).isNull();
    }

    /**
     * Получение полного списка сессий
     * Метод Collection<FilmSession> findAll()
     */
    @Test
    public void whenFindAllThenGetAllFilmSessions() {
        var expectedFilmSessions = Arrays.asList(
                new FilmSession(1, 1, 1, LocalDateTime.of(2023, 11, 19, 10, 0, 0), LocalDateTime.of(2023, 11, 19, 13, 9, 0), 200),
                new FilmSession(2, 2, 1, LocalDateTime.of(2023, 11, 19, 13, 30, 0), LocalDateTime.of(2023, 11, 19, 15, 52, 0), 200),
                new FilmSession(3, 3, 1, LocalDateTime.of(2023, 11, 19, 16, 30, 0), LocalDateTime.of(2023, 11, 19, 18, 52, 0), 200),
                new FilmSession(4, 4, 1, LocalDateTime.of(2023, 11, 19, 19, 30, 0), LocalDateTime.of(2023, 11, 19, 22, 19, 0), 200),
                new FilmSession(5, 5, 2, LocalDateTime.of(2023, 11, 19, 10, 0, 0), LocalDateTime.of(2023, 11, 19, 12, 19, 0), 300),
                new FilmSession(6, 1, 2, LocalDateTime.of(2023, 11, 19, 13, 0, 0), LocalDateTime.of(2023, 11, 19, 16, 9, 0), 300),
                new FilmSession(7, 2, 2, LocalDateTime.of(2023, 11, 19, 17, 0, 0), LocalDateTime.of(2023, 11, 19, 19, 22, 0), 300),
                new FilmSession(8, 3, 2, LocalDateTime.of(2023, 11, 19, 20, 0, 0), LocalDateTime.of(2023, 11, 19, 22, 22, 0), 300),
                new FilmSession(9, 4, 3, LocalDateTime.of(2023, 11, 19, 10, 0, 0), LocalDateTime.of(2023, 11, 19, 12, 49, 0), 250),
                new FilmSession(10, 5, 3, LocalDateTime.of(2023, 11, 19, 14, 0, 0), LocalDateTime.of(2023, 11, 19, 16, 19, 0), 250),
                new FilmSession(11, 1, 3, LocalDateTime.of(2023, 11, 19, 17, 0, 0), LocalDateTime.of(2023, 11, 19, 20, 9, 0), 250),
                new FilmSession(12, 2, 3, LocalDateTime.of(2023, 11, 19, 21, 0, 0), LocalDateTime.of(2023, 11, 19, 23, 22, 0), 250),
                new FilmSession(13, 1, 1, LocalDateTime.of(2023, 11, 20, 10, 0, 0), LocalDateTime.of(2023, 11, 20, 13, 9, 0), 200),
                new FilmSession(14, 2, 1, LocalDateTime.of(2023, 11, 20, 13, 30, 0), LocalDateTime.of(2023, 11, 20, 15, 52, 0), 200),
                new FilmSession(15, 3, 1, LocalDateTime.of(2023, 11, 20, 16, 30, 0), LocalDateTime.of(2023, 11, 20, 18, 52, 0), 200),
                new FilmSession(16, 4, 1, LocalDateTime.of(2023, 11, 20, 19, 30, 0), LocalDateTime.of(2023, 11, 20, 22, 19, 0), 200),
                new FilmSession(17, 5, 2, LocalDateTime.of(2023, 11, 20, 10, 0, 0), LocalDateTime.of(2023, 11, 20, 12, 19, 0), 300),
                new FilmSession(18, 1, 2, LocalDateTime.of(2023, 11, 20, 13, 0, 0), LocalDateTime.of(2023, 11, 20, 16, 9, 0), 300),
                new FilmSession(19, 2, 2, LocalDateTime.of(2023, 11, 20, 17, 0, 0), LocalDateTime.of(2023, 11, 20, 19, 22, 0), 300),
                new FilmSession(20, 3, 2, LocalDateTime.of(2023, 11, 20, 20, 0, 0), LocalDateTime.of(2023, 11, 20, 22, 22, 0), 300),
                new FilmSession(21, 4, 3, LocalDateTime.of(2023, 11, 20, 10, 0, 0), LocalDateTime.of(2023, 11, 20, 12, 49, 0), 250),
                new FilmSession(22, 5, 3, LocalDateTime.of(2023, 11, 20, 14, 0, 0), LocalDateTime.of(2023, 11, 20, 16, 19, 0), 250),
                new FilmSession(23, 1, 3, LocalDateTime.of(2023, 11, 20, 17, 0, 0), LocalDateTime.of(2023, 11, 20, 20, 9, 0), 250),
                new FilmSession(24, 2, 3, LocalDateTime.of(2023, 11, 20, 21, 0, 0), LocalDateTime.of(2023, 11, 20, 23, 22, 0), 250),
                new FilmSession(25, 1, 1, LocalDateTime.of(2023, 11, 21, 10, 0, 0), LocalDateTime.of(2023, 11, 21, 13, 9, 0), 200),
                new FilmSession(26, 2, 1, LocalDateTime.of(2023, 11, 21, 13, 30, 0), LocalDateTime.of(2023, 11, 21, 15, 52, 0), 200),
                new FilmSession(27, 3, 1, LocalDateTime.of(2023, 11, 21, 16, 30, 0), LocalDateTime.of(2023, 11, 21, 18, 52, 0), 200),
                new FilmSession(28, 4, 1, LocalDateTime.of(2023, 11, 21, 19, 30, 0), LocalDateTime.of(2023, 11, 21, 22, 19, 0), 200),
                new FilmSession(29, 5, 2, LocalDateTime.of(2023, 11, 21, 10, 0, 0), LocalDateTime.of(2023, 11, 21, 12, 19, 0), 300),
                new FilmSession(30, 1, 2, LocalDateTime.of(2023, 11, 21, 13, 0, 0), LocalDateTime.of(2023, 11, 21, 16, 9, 0), 300),
                new FilmSession(31, 2, 2, LocalDateTime.of(2023, 11, 21, 17, 0, 0), LocalDateTime.of(2023, 11, 21, 19, 22, 0), 300),
                new FilmSession(32, 3, 2, LocalDateTime.of(2023, 11, 21, 20, 0, 0), LocalDateTime.of(2023, 11, 21, 22, 22, 0), 300),
                new FilmSession(33, 4, 3, LocalDateTime.of(2023, 11, 21, 10, 0, 0), LocalDateTime.of(2023, 11, 21, 12, 49, 0), 250),
                new FilmSession(34, 5, 3, LocalDateTime.of(2023, 11, 21, 14, 0, 0), LocalDateTime.of(2023, 11, 21, 16, 19, 0), 250),
                new FilmSession(35, 1, 3, LocalDateTime.of(2023, 11, 21, 17, 0, 0), LocalDateTime.of(2023, 11, 21, 20, 9, 0), 250),
                new FilmSession(36, 2, 3, LocalDateTime.of(2023, 11, 21, 21, 0, 0), LocalDateTime.of(2023, 11, 21, 23, 22, 0), 250),
                new FilmSession(37, 1, 1, LocalDateTime.of(2023, 11, 22, 10, 0, 0), LocalDateTime.of(2023, 11, 22, 13, 9, 0), 200),
                new FilmSession(38, 2, 1, LocalDateTime.of(2023, 11, 22, 13, 30, 0), LocalDateTime.of(2023, 11, 22, 15, 52, 0), 200),
                new FilmSession(39, 3, 1, LocalDateTime.of(2023, 11, 22, 16, 30, 0), LocalDateTime.of(2023, 11, 22, 18, 52, 0), 200),
                new FilmSession(40, 4, 1, LocalDateTime.of(2023, 11, 22, 19, 30, 0), LocalDateTime.of(2023, 11, 22, 22, 19, 0), 200),
                new FilmSession(41, 5, 2, LocalDateTime.of(2023, 11, 22, 10, 0, 0), LocalDateTime.of(2023, 11, 22, 12, 19, 0), 300),
                new FilmSession(42, 1, 2, LocalDateTime.of(2023, 11, 22, 13, 0, 0), LocalDateTime.of(2023, 11, 22, 16, 9, 0), 300),
                new FilmSession(43, 2, 2, LocalDateTime.of(2023, 11, 22, 17, 0, 0), LocalDateTime.of(2023, 11, 22, 19, 22, 0), 300),
                new FilmSession(44, 3, 2, LocalDateTime.of(2023, 11, 22, 20, 0, 0), LocalDateTime.of(2023, 11, 22, 22, 22, 0), 300),
                new FilmSession(45, 4, 3, LocalDateTime.of(2023, 11, 22, 10, 0, 0), LocalDateTime.of(2023, 11, 22, 12, 49, 0), 250),
                new FilmSession(46, 5, 3, LocalDateTime.of(2023, 11, 22, 14, 0, 0), LocalDateTime.of(2023, 11, 22, 16, 19, 0), 250),
                new FilmSession(47, 1, 3, LocalDateTime.of(2023, 11, 22, 17, 0, 0), LocalDateTime.of(2023, 11, 22, 20, 9, 0), 250),
                new FilmSession(48, 2, 3, LocalDateTime.of(2023, 11, 22, 21, 0, 0), LocalDateTime.of(2023, 11, 22, 23, 22, 0), 250),
                new FilmSession(49, 1, 1, LocalDateTime.of(2023, 11, 23, 10, 0, 0), LocalDateTime.of(2023, 11, 23, 13, 9, 0), 200),
                new FilmSession(50, 2, 1, LocalDateTime.of(2023, 11, 23, 13, 30, 0), LocalDateTime.of(2023, 11, 23, 15, 52, 0), 200),
                new FilmSession(51, 3, 1, LocalDateTime.of(2023, 11, 23, 16, 30, 0), LocalDateTime.of(2023, 11, 23, 18, 52, 0), 200),
                new FilmSession(52, 4, 1, LocalDateTime.of(2023, 11, 23, 19, 30, 0), LocalDateTime.of(2023, 11, 23, 22, 19, 0), 200),
                new FilmSession(53, 5, 2, LocalDateTime.of(2023, 11, 23, 10, 0, 0), LocalDateTime.of(2023, 11, 23, 12, 19, 0), 300),
                new FilmSession(54, 1, 2, LocalDateTime.of(2023, 11, 23, 13, 0, 0), LocalDateTime.of(2023, 11, 23, 16, 9, 0), 300),
                new FilmSession(55, 2, 2, LocalDateTime.of(2023, 11, 23, 17, 0, 0), LocalDateTime.of(2023, 11, 23, 19, 22, 0), 300),
                new FilmSession(56, 3, 2, LocalDateTime.of(2023, 11, 23, 20, 0, 0), LocalDateTime.of(2023, 11, 23, 22, 22, 0), 300),
                new FilmSession(57, 4, 3, LocalDateTime.of(2023, 11, 23, 10, 0, 0), LocalDateTime.of(2023, 11, 23, 12, 49, 0), 250),
                new FilmSession(58, 5, 3, LocalDateTime.of(2023, 11, 23, 14, 0, 0), LocalDateTime.of(2023, 11, 23, 16, 19, 0), 250),
                new FilmSession(59, 1, 3, LocalDateTime.of(2023, 11, 23, 17, 0, 0), LocalDateTime.of(2023, 11, 23, 20, 9, 0), 250),
                new FilmSession(60, 2, 3, LocalDateTime.of(2023, 11, 23, 21, 0, 0), LocalDateTime.of(2023, 11, 23, 23, 22, 0), 250),
                new FilmSession(61, 1, 1, LocalDateTime.of(2023, 11, 24, 10, 0, 0), LocalDateTime.of(2023, 11, 24, 13, 9, 0), 250),
                new FilmSession(62, 2, 1, LocalDateTime.of(2023, 11, 24, 13, 30, 0), LocalDateTime.of(2023, 11, 24, 15, 52, 0), 250),
                new FilmSession(63, 3, 1, LocalDateTime.of(2023, 11, 24, 16, 30, 0), LocalDateTime.of(2023, 11, 24, 18, 52, 0), 250),
                new FilmSession(64, 4, 1, LocalDateTime.of(2023, 11, 24, 19, 30, 0), LocalDateTime.of(2023, 11, 24, 22, 19, 0), 250),
                new FilmSession(65, 5, 2, LocalDateTime.of(2023, 11, 24, 10, 0, 0), LocalDateTime.of(2023, 11, 24, 12, 19, 0), 350),
                new FilmSession(66, 1, 2, LocalDateTime.of(2023, 11, 24, 13, 0, 0), LocalDateTime.of(2023, 11, 24, 16, 9, 0), 350),
                new FilmSession(67, 2, 2, LocalDateTime.of(2023, 11, 24, 17, 0, 0), LocalDateTime.of(2023, 11, 24, 19, 22, 0), 350),
                new FilmSession(68, 3, 2, LocalDateTime.of(2023, 11, 24, 20, 0, 0), LocalDateTime.of(2023, 11, 24, 22, 22, 0), 350),
                new FilmSession(69, 4, 3, LocalDateTime.of(2023, 11, 24, 10, 0, 0), LocalDateTime.of(2023, 11, 24, 12, 49, 0), 300),
                new FilmSession(70, 5, 3, LocalDateTime.of(2023, 11, 24, 14, 0, 0), LocalDateTime.of(2023, 11, 24, 16, 19, 0), 300),
                new FilmSession(71, 1, 3, LocalDateTime.of(2023, 11, 24, 17, 0, 0), LocalDateTime.of(2023, 11, 24, 20, 9, 0), 300),
                new FilmSession(72, 2, 3, LocalDateTime.of(2023, 11, 24, 21, 0, 0), LocalDateTime.of(2023, 11, 24, 23, 22, 0), 300),
                new FilmSession(73, 1, 1, LocalDateTime.of(2023, 11, 25, 10, 0, 0), LocalDateTime.of(2023, 11, 25, 13, 9, 0), 250),
                new FilmSession(74, 2, 1, LocalDateTime.of(2023, 11, 25, 13, 30, 0), LocalDateTime.of(2023, 11, 25, 15, 52, 0), 250),
                new FilmSession(75, 3, 1, LocalDateTime.of(2023, 11, 25, 16, 30, 0), LocalDateTime.of(2023, 11, 25, 18, 52, 0), 250),
                new FilmSession(76, 4, 1, LocalDateTime.of(2023, 11, 25, 19, 30, 0), LocalDateTime.of(2023, 11, 25, 22, 19, 0), 250),
                new FilmSession(77, 5, 2, LocalDateTime.of(2023, 11, 25, 10, 0, 0), LocalDateTime.of(2023, 11, 25, 12, 19, 0), 350),
                new FilmSession(78, 1, 2, LocalDateTime.of(2023, 11, 25, 13, 0, 0), LocalDateTime.of(2023, 11, 25, 16, 9, 0), 350),
                new FilmSession(79, 2, 2, LocalDateTime.of(2023, 11, 25, 17, 0, 0), LocalDateTime.of(2023, 11, 25, 19, 22, 0), 350),
                new FilmSession(80, 3, 2, LocalDateTime.of(2023, 11, 25, 20, 0, 0), LocalDateTime.of(2023, 11, 25, 22, 22, 0), 350),
                new FilmSession(81, 4, 3, LocalDateTime.of(2023, 11, 25, 10, 0, 0), LocalDateTime.of(2023, 11, 25, 12, 49, 0), 300),
                new FilmSession(82, 5, 3, LocalDateTime.of(2023, 11, 25, 14, 0, 0), LocalDateTime.of(2023, 11, 25, 16, 19, 0), 300),
                new FilmSession(83, 1, 3, LocalDateTime.of(2023, 11, 25, 17, 0, 0), LocalDateTime.of(2023, 11, 25, 20, 9, 0), 300),
                new FilmSession(84, 2, 3, LocalDateTime.of(2023, 11, 25, 21, 0, 0), LocalDateTime.of(2023, 11, 25, 23, 22, 0), 300));
        var actualFilmSessions = sql2oFilmSessionRepository.findAll();
        Assertions.assertThat(actualFilmSessions).usingRecursiveComparison().isEqualTo(expectedFilmSessions);
    }
}