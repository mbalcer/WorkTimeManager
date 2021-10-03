package pl.mbalcer.service.impl;

import pl.mbalcer.model.MonthYear;
import pl.mbalcer.service.FileService;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ApplicationScoped
public class FileServiceImpl implements FileService {
    private final String formatFilename = "%02d. %d";

    @Override
    public void saveFile(Path path, String body, MonthYear monthYear) {
        Path filePath = path.resolve(String.format(formatFilename, monthYear.getMonth(), monthYear.getYear()) + ".txt");
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.writeString(filePath, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
