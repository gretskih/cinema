package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.User;

import java.util.Optional;
import java.util.Properties;

/**
 * Тесты для слоя: Репозиторий
 * Модель: User
 */
public class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;
    private static Sql2o sql2o;

    /**
     * Инициализация полей репизитория sql2oUserRepository, sql2o
     * @throws Exception
     */
    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
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
        Optional<User> actualUserOptional = sql2oUserRepository.save(expectedUser);

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
        sql2oUserRepository.save(user);
        Optional<User> actualUserOptional = sql2oUserRepository.save(user);

        Assertions.assertThat(actualUserOptional).isEmpty();
        Assertions.assertThat(actualUserOptional).isNotPresent();
    }

    /**
     * Поиск существующего в базе пользователя по почте email и паролю password
     * Метод Optional<User> findByEmailAndPassword(String email, String password)
     */
    @Test
    public void whenSaveNewUserAndFindByEmailAndPasswordThenGetNewUser() {
        User expectedUser = new User(3, "FullName", "fullname@name.org", "1111");
        sql2oUserRepository.save(expectedUser);
        Optional<User> actualUserOptional = sql2oUserRepository.findByEmailAndPassword(expectedUser.getEmail(), expectedUser.getPassword());
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
        sql2oUserRepository.save(user);
        Optional<User> actualUserOptional = sql2oUserRepository.findByEmailAndPassword("nonexistent@user.org", "0000");
        Assertions.assertThat(actualUserOptional).isEmpty();
        Assertions.assertThat(actualUserOptional).isNotPresent();
    }
}
