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

/**
 * Тесты для слоя: Сервис
 * Модель: User
 */
class SimpleUserServiceTest {

    private static SimpleUserService simpleUserService;

    private static Sql2o sql2o;

    /**
     * Инициализация полей сервиса simpleUserService, sql2o
     * @throws Exception
     */
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

    /**
     * Удаление всех строк из таблицы users
     */
    @AfterEach
    public void clearUsers() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users", false);
            query.executeUpdate();
        }
    }

    /**
     * Сохранение нового пользователя
     * Метод Optional<User> save(User user)
     */
    @Test
    public void whenSaveNewUserThenGetNewUser() {
        User expectedUser = new User(1, "Name", "name@name.org", "1122");
        Optional<User> actualUserOptional = simpleUserService.save(expectedUser);

        Assertions.assertThat(actualUserOptional)
                .isPresent()
                .isNotEmpty()
                .contains(expectedUser)
                .hasValueSatisfying(file -> {
                    Assertions.assertThat(file).usingRecursiveComparison().isEqualTo(expectedUser);
                });
    }

    /**
     * Сохранение пользователя, который существует в таблице users
     * Метод Optional<User> save(User user)
     */
    @Test
    public void whenSaveExistingUserThenGetEmptyUser() {
        User user = new User(1, "Name", "name@name.org", "1122");
        simpleUserService.save(user);
        Optional<User> actualUser = simpleUserService.save(user);

        Assertions.assertThat(actualUser).isEmpty();
        Assertions.assertThat(actualUser).isNotPresent();
    }

    /**
     * Поиск существующего в базе пользователя по почте email и паролю password
     * Метод Optional<User> findByEmailAndPassword(String email, String password)
     */
    @Test
    public void whenSaveNewUserAndFindByEmailAndPasswordThenGetNewUser() {
        User expectedUser = new User(3, "FullName", "fullname@name.org", "1111");
        simpleUserService.save(expectedUser);
        Optional<User> actualUserOptional = simpleUserService.findByEmailAndPassword(expectedUser.getEmail(), expectedUser.getPassword());
        Assertions.assertThat(actualUserOptional)
                .isPresent()
                .isNotEmpty()
                .contains(expectedUser)
                .hasValueSatisfying(file -> {
                    Assertions.assertThat(file).usingRecursiveComparison().isEqualTo(expectedUser);
                });
    }

    /**
     * Поиск отсутствующего в базе пользователя по почте email и паролю password
     * Метод Optional<User> findByEmailAndPassword(String email, String password)
     */
    @Test
    public void whenSaveNewUserAndFindByEmailAndPasswordThenGetEmptyUser() {
        User user = new User(2, "UserName", "user@name.org", "1555");
        simpleUserService.save(user);
        Optional<User> actualUser = simpleUserService.findByEmailAndPassword("nonexistent@user.org", "0000");
        Assertions.assertThat(actualUser).isEmpty();
        Assertions.assertThat(actualUser).isNotPresent();
    }
}