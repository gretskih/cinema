package ru.job4j.cinema.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.Sql2oTicketRepository;

import java.util.Optional;
import java.util.Properties;

/**
 * Тесты для слоя: Сервис
 * Модель: Ticket
 */
class SimpleTicketServiceTest {

    private static SimpleTicketService simpleTicketService;
    private static Sql2o sql2o;

    /**
     * Инициализация полей сервиса simpleTicketService, sql2o
     * @throws Exception
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = SimpleTicketServiceTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        Sql2oTicketRepository sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
        simpleTicketService = new SimpleTicketService(sql2oTicketRepository);
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
        Ticket actualTicked = simpleTicketService.save(expectedTicked).get();

        Assertions.assertThat(actualTicked).usingRecursiveComparison().isEqualTo(expectedTicked);
    }

    /**
     * Сохранение билета, который существует в таблице tickets
     * Метод Optional<Ticket> save(Ticket ticket)
     */
    @Test
    public void whenSaveExistingTicketThenGetEmptyTicket() {
        Ticket ticked = new Ticket(1, 1, 1, 1, 1);
        simpleTicketService.save(ticked);
        Optional<Ticket> actualTicked = simpleTicketService.save(ticked);

        Assertions.assertThat(actualTicked).isEqualTo(Optional.empty());
    }
}