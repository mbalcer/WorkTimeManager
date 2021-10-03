package pl.mbalcer.cmd;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.repository.MonthYearRepository;
import pl.mbalcer.service.ReportService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Dependent
@Command(name = "print", mixinStandardHelpOptions = true)
public class PrintCommand implements Runnable {

    @Option(names = {"--month", "-m"})
    Integer month;

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
        MonthYear monthYear = monthYearRepository.findByMonthAndYear(month, today.getYear());
        String report = reportService.createMonthlyWorkTimeReport(monthYear);
        System.out.println(report);
    }
}
