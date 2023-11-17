package ru.job4j.cinema.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;

import java.util.Optional;
import java.util.Properties;

/**
 * Тесты для слоя: Репозиторий
 * Модель: File
 */
class Sql2oFileRepositoryTest {

    private static Sql2oFileRepository sql2oFileRepository;

    /**
     * Инициализация поля репизитория sql2oFileRepository
     * @throws Exception
     */
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

    /**
     * Поиск существующего в базе файла по id
     * Метод File findById(int id)
     */
    @Test
    public void whenFindByIdThenGetFile() {
        File expectedFile = new File(2, "The Shawshank Redemption", "src/main/resources/files/The_Shawshank_Redemption.jpg");
        Optional<File> actualFileOptional = sql2oFileRepository.findById(expectedFile.getId());

        Assertions.assertThat(actualFileOptional)
                .isPresent()
                .isNotEmpty()
                .contains(expectedFile)
                .hasValueSatisfying(file -> {
                    Assertions.assertThat(file).usingRecursiveComparison().isEqualTo(expectedFile);
                });
    }

    /**
     * Поиск отсутствующего в базе файла по id
     * Метод File findById(int id)
     */
    @Test
    public void whenFindByIdThenGetSomeFile() {
        Optional<File> actualFileOptional = sql2oFileRepository.findById(100);

        Assertions.assertThat(actualFileOptional).isEmpty();
        Assertions.assertThat(actualFileOptional).isNotPresent();
    }
}