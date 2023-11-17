package ru.job4j.cinema.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.repository.FileRepository;
import ru.job4j.cinema.dto.FileDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class SimpleFileService implements FileService {
    private final FileRepository fileRepository;
    private static final Logger LOG = LoggerFactory.getLogger(SimpleFileService.class);

    public SimpleFileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public Optional<FileDto> getFileById(int id) {
        var file = fileRepository.findById(id);
        try {
            var content = readFileAsBytes(file.getPath());
            return Optional.of(new FileDto(file.getName(), content));
        } catch (Exception e) {
            LOG.error(e.toString());
        }
        return Optional.empty();
    }

    private byte[] readFileAsBytes(String path) throws Exception {
        return Files.readAllBytes(Path.of(path));
    }
}
