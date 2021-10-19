package pl.mbalcer.cmd;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;
import pl.mbalcer.repository.MonthYearRepository;
import pl.mbalcer.repository.WorkTimeRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Dependent
@Command(name = "calculate",
        mixinStandardHelpOptions = true,
        sortOptions = false,
        version = "Work Time Manager 1.0",
        header = "",
        description = "",
        synopsisHeading = "%n",
        descriptionHeading = "%nDescription:%n",
        optionListHeading = "%nOptions:%n")
public class CalculateCommand implements Runnable {
    @Option(names = {"--month", "-m"}, description = "")
    Integer month;

    @Option(names = {"--year", "-y"}, description = "")
    Integer year;

    @Inject
    WorkTimeRepository workTimeRepository;

    @Inject
    MonthYearRepository monthYearRepository;

    @Override
    @Transactional
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        if (month == null) {
            month = now.getMonthValue();
        }
        if (year == null) {
            year = now.getYear();
        }

        MonthYear monthYear = monthYearRepository.findByMonthAndYear(month, year);
        List<WorkTime> workTimes = workTimeRepository.findAllByMonth(monthYear);
        long sumMinutes = workTimes.stream()
                .filter(workTime -> workTime.getStartTime() != null && workTime.getEndTime() != null)
                .mapToLong(workTime -> ChronoUnit.MINUTES.between(workTime.getStartTime(), workTime.getEndTime()))
                .sum();
        long sumHours = sumMinutes / 60;

        log.info(String.format("You worked %d hours and %d minutes in a month (%d.%d)", sumHours, sumMinutes - (sumHours * 60), month, year));
    }
}
