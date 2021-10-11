package pl.mbalcer.cmd;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.repository.MonthYearRepository;
import pl.mbalcer.service.ReportService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Slf4j
@Dependent
@Command(name = "print",
        mixinStandardHelpOptions = true,
        sortOptions = false,
        version = "Work Time Manager 1.0",
        header = "Print monthly work time report",
        description = "The command prints monthly work time report.",
        synopsisHeading = "%n",
        descriptionHeading = "%nDescription:%n",
        optionListHeading = "%nOptions:%n")
public class PrintCommand implements Runnable {

    @Option(names = {"--month", "-m"}, description = "Month from which the report is to be printed")
    Integer month;

    @Option(names = {"--year", "-y"}, description = "Year from which the report is to be printed")
    Integer year;

    @Inject
    MonthYearRepository monthYearRepository;

    @Inject
    ReportService reportService;

    @Transactional
    @Override
    public void run() {
        LocalDate today = LocalDate.now();
        if (month == null) {
            month = today.getMonthValue();
        }
        if (year == null) {
            year = today.getYear();
        }
        MonthYear monthYear = monthYearRepository.findByMonthAndYear(month, year);
        String report = reportService.createMonthlyWorkTimeReport(monthYear);
        log.info("Monthly work time report: \n{}", report);
    }
}
