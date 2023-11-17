package ru.job4j.cinema.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.Sql2oFileRepository;
import ru.job4j.cinema.repository.Sql2oUserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;

/**
 * Тесты для слоя: Сервис
 * Модель: FileDto
 */
class SimpleFileServiceTest {

    private static Sql2oFileRepository sql2oFileRepository;
    private static SimpleFileService simpleFileService;

    /**
     * Инициализация полей сервиса sql2oFileRepository, simpleFileService
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
        var sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        simpleFileService = new SimpleFileService(sql2oFileRepository);
    }

    /**
     * Получение объекта FileDto по id существующего файла
     * Метод Optional<FileDto> getFileById(int id)
     */
    @Test
    public void whenFindByIdThenGetFile() throws Exception {
        File file = new File(2, "The Shawshank Redemption", "src/main/resources/files/The_Shawshank_Redemption.jpg");
        FileDto expectedFileDto = new FileDto(file.getName(), Files.readAllBytes(Path.of(file.getPath())));
        Optional<FileDto> actualFileDtoOptional = simpleFileService.getFileById(file.getId());

        Assertions.assertThat(actualFileDtoOptional)
                .isPresent()
                .isNotEmpty()
                .hasValueSatisfying(fileDto -> {
                    Assertions.assertThat(fileDto.getName()).isEqualTo(expectedFileDto.getName());
                });
    }

    /**
     * Получение объекта FileDto по id отсутствующего файла
     * Метод Optional<FileDto> getFileById(int id)
     */
    @Test
    public void whenFindByIdThenGetSomeFile() {
        Optional<FileDto> actualFileDTO = simpleFileService.getFileById(100);
        Assertions.assertThat(actualFileDTO).isEmpty();
        Assertions.assertThat(actualFileDTO).isNotPresent();
    }
}