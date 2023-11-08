package ru.job4j.cinema.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.Sql2oUserRepository;

import java.util.Optional;
import java.util.Properties;

class SimpleUserServiceTest {

    private static SimpleUserService simpleUserService;

    private static Sql2o sql2o;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = SimpleUserServiceTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        Sql2oUserRepository sql2oUserRepository = new Sql2oUserRepository(sql2o);
        simpleUserService = new SimpleUserService(sql2oUserRepository);
    }

    @AfterEach
    public void clearUsers() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users", false);
            query.executeUpdate();
        }
    }

    @Test
    public void whenSaveNewUserThenGetNewUser() {
        User expectedUser = new User(1, "Name", "name@name.org", "1122");
        User actualUser = simpleUserService.save(expectedUser).get();

        Assertions.assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    public void whenSaveExistingUserThenGetEmptyUser() {
        User user = new User(1, "Name", "name@name.org", "1122");
        simpleUserService.save(user);
        Optional<User> actualUser = simpleUserService.save(user);

        Assertions.assertThat(actualUser).isEqualTo(Optional.empty());
    }
}