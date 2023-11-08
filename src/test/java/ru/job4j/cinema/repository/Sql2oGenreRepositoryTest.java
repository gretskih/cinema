package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Genre;

import java.util.Properties;

class Sql2oGenreRepositoryTest {

    private static Sql2oGenreRepository sql2oGenreRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oGenreRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
    }

    @Test
    void whenFindByIdThenGetGenre() {
        Genre expectedGenre = new Genre(3, "Криминал");
        Genre actualGenre = sql2oGenreRepository.findById(expectedGenre.getId());
        Assertions.assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void whenFindByIdThenGetSomeGenre() {
        Genre expectedGenre = new Genre(3, "Криминал");
        Genre actualGenre = sql2oGenreRepository.findById(expectedGenre.getId() + 1);
        Assertions.assertThat(actualGenre).usingRecursiveComparison().isNotEqualTo(expectedGenre);
    }
}