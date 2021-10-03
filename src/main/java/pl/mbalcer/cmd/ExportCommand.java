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
import java.nio.file.Path;
import java.time.LocalDate;

@Slf4j
@Dependent
@Command(name = "export", mixinStandardHelpOptions = true)
public class ExportCommand implements Runnable {

    @Option(names = {"--month", "-m"})
    Integer month;

    @Inject
    FileService fileService;

    @Inject
    MonthYearRepository monthYearRepository;

    @Inject
    ReportService reportService;

    @Transactional
    @Override
    public void run() {
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }

        Path path = Path.of("D:/");
        MonthYear monthYear = monthYearRepository.findByMonthAndYear(month, 2021);
        String monthlyWorkTimeReport = reportService.createMonthlyWorkTimeReport(monthYear);

        fileService.saveFile(path, monthlyWorkTimeReport, monthYear);
        log.info("Content has been written to a file: {}", path);
    }
}
