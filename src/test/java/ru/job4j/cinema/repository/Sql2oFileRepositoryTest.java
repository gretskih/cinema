package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;

import java.util.Properties;

class Sql2oFileRepositoryTest {

    private static Sql2oFileRepository sql2oFileRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFileRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }

    @Test
    public void whenFindByIdThenGetFile() {
        File expectedFile = new File(2, "The Shawshank Redemption", "files\\The_Shawshank_Redemption.jpg");
        File actualFile = sql2oFileRepository.findById(expectedFile.getId());

        Assertions.assertThat(actualFile).usingRecursiveComparison().isEqualTo(expectedFile);
    }

    @Test
    public void whenFindByIdThenGetSomeFile() {
        File expectedFile = new File(2, "The Shawshank Redemption", "files\\The_Shawshank_Redemption.jpg");
        File actualFile = sql2oFileRepository.findById(expectedFile.getId() + 1);

        Assertions.assertThat(actualFile).usingRecursiveComparison().isNotEqualTo(expectedFile);
    }
}