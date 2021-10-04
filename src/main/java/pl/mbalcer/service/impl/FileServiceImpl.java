package pl.mbalcer.service.impl;

import lombok.extern.slf4j.Slf4j;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.service.FileService;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ApplicationScoped
@Slf4j
public class FileServiceImpl implements FileService {
    private final String formatFilename = "%02d. %d";

    @Override
    public void saveFile(Path path, String body, MonthYear monthYear) {
        Path filePath = path.resolve(String.format(formatFilename, monthYear.getMonth(), monthYear.getYear()) + ".txt");
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                log.error("An error occurred while creating a file: {}", filePath);
            }
        }

        try {
            Files.writeString(filePath, body);
        } catch (IOException e) {
            log.error("An error occurred while writing content to the file: {}", filePath);
        }
    }

    @Override
    public void createDirectory(Path path) {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            log.error("An error occurred while creating a directory: {}", path);
        }
    }
}
