package pl.mbalcer.cmd;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.repository.MonthYearRepository;
import pl.mbalcer.service.FileService;
import pl.mbalcer.service.ReportService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@Slf4j
@Dependent
@Command(name = "export", mixinStandardHelpOptions = true)
public class ExportCommand implements Runnable {
    private static final String WORK_TIME = "work-time";

    @Option(names = {"--directory", "--dir", "-d"}, defaultValue = "C:/")
    String directory;

    @Option(names = {"--month", "-m"})
    Integer month;

    @Option(names = {"--year", "-y"})
    Integer year;

    @Inject
    FileService fileService;

    @Inject
    ReportService reportService;

    @Inject
    MonthYearRepository monthYearRepository;

    @Transactional
    @Override
    public void run() {
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        Path path = Path.of(directory).resolve(WORK_TIME);
        if (!Files.exists(path)) {
            fileService.createDirectory(path);
        }

        MonthYear monthYear = monthYearRepository.findByMonthAndYear(month, year);
        String monthlyWorkTimeReport = reportService.createMonthlyWorkTimeReport(monthYear);

        fileService.saveFile(path, monthlyWorkTimeReport, monthYear);
        log.info("Content has been written to a file: {}", path);
    }
}
