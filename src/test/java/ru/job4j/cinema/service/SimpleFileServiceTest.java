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
import java.util.Properties;

class SimpleFileServiceTest {

    private static Sql2oFileRepository sql2oFileRepository;
    private static SimpleFileService simpleFileService;

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

    @Test
    public void whenFindByIdThenGetFile() throws Exception {
        File file = new File(2, "The Shawshank Redemption", "files\\The_Shawshank_Redemption.jpg");
        FileDto expectedFileDto = new FileDto(file.getName(), Files.readAllBytes(Path.of(file.getPath())));
        FileDto actualFileDTO = simpleFileService.getFileById(file.getId()).get();

        Assertions.assertThat(actualFileDTO).usingRecursiveComparison().isEqualTo(expectedFileDto);
    }

    @Test
    public void whenFindByIdThenGetSomeFile() throws Exception {
        File file = new File(2, "The Shawshank Redemption", "files\\The_Shawshank_Redemption.jpg");
        FileDto expectedFileDto = new FileDto(file.getName(), Files.readAllBytes(Path.of(file.getPath())));
        FileDto actualFileDTO = simpleFileService.getFileById(file.getId() + 1).get();

        Assertions.assertThat(actualFileDTO).usingRecursiveComparison().isNotEqualTo(expectedFileDto);
    }
}