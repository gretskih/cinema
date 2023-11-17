package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;
import java.util.Properties;

/**
 * Тесты для слоя: Репозиторий
 * Модель: Ticket
 */
public class Sql2oTicketRepositoryTest {

    private static Sql2oTicketRepository sql2oTicketRepository;
    private static Sql2o sql2o;

    /**
     * Инициализация полей репизитория sql2oTicketRepository, sql2o
     * @throws Exception
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    /**
     * Удаление всех строк из таблицы tickets
     */
    @AfterEach
    public void clearTickets() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM tickets", false);
            query.executeUpdate();
        }
    }

    /**
     * Сохранение нового билета
     * Метод Optional<Ticket> save(Ticket ticket)
     */
    @Test
    public void whenSaveNewTicketThenGetNewTicket() {
        Ticket expectedTicked = new Ticket(1, 1, 1, 1, 1);
        Ticket actualTicked = sql2oTicketRepository.save(expectedTicked).get();

        Assertions.assertThat(actualTicked).usingRecursiveComparison().isEqualTo(expectedTicked);
    }

    /**
     * Сохранение билета, который существует в таблице tickets
     * Метод Optional<Ticket> save(Ticket ticket)
     */
    @Test
    public void whenSaveExistingTicketThenGetEmptyTicket() {
        Ticket ticked = new Ticket(1, 1, 1, 1, 1);
        sql2oTicketRepository.save(ticked);
        Optional<Ticket> actualTicked = sql2oTicketRepository.save(ticked);

        Assertions.assertThat(actualTicked).isEqualTo(Optional.empty());
    }
}
