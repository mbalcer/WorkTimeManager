package pl.mbalcer.service;

import pl.mbalcer.model.MonthYear;

import java.nio.file.Path;

public interface FileService {
    void saveFile(Path path, String body, MonthYear monthYear);

    void createDirectory(Path path);
}
