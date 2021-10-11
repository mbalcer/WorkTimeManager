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
@Command(name = "export",
        mixinStandardHelpOptions = true,
        sortOptions = false,
        version = "Work Time Manager 1.0",
        header = "Export work time report to file",
        description = "The command creates a file with the name of a given month in a given directory and writes a work time report in it",
        synopsisHeading = "%n",
        descriptionHeading = "%nDescription:%n",
        optionListHeading = "%nOptions:%n")
public class ExportCommand implements Runnable {
    @Option(names = {"--directory", "--dir", "-d"}, required = true, description = "Path to the directory where the application is to save the reports")
    String directory;

    @Option(names = {"--month", "-m"}, description = "Month from which the report is to be generated")
    Integer month;

    @Option(names = {"--year", "-y"}, description = "Year from which the report is to be generated")
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
        Path path = Path.of(directory);
        if (!Files.exists(path)) {
            log.error("The directory specified as a parameter doesn't exist");
        } else {
            MonthYear monthYear = monthYearRepository.findByMonthAndYear(month, year);
            String monthlyWorkTimeReport = reportService.createMonthlyWorkTimeReport(monthYear);

            fileService.saveFile(path, monthlyWorkTimeReport, monthYear);
            log.info("Content has been written to a file: {}", path);
        }
    }
}
