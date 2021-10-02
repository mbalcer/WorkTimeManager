package pl.mbalcer.cmd;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.WorkTime;
import pl.mbalcer.repository.MonthYearRepository;
import pl.mbalcer.repository.WorkTimeRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Dependent
@Command(name = "print", mixinStandardHelpOptions = true)
public class PrintCommand implements Runnable {

    @Option(names = {"--month", "-m"})
    Integer month;

    @Inject
    MonthYearRepository monthYearRepository;

    @Inject
    WorkTimeRepository workTimeRepository;

    @Transactional
    @Override
    public void run() {
        LocalDate today = LocalDate.now();
        if (month == null) {
            month = today.getMonthValue();
        }
        List<WorkTime> allByMonth = workTimeRepository.findAllByMonth(monthYearRepository.findByMonthAndYear(month, today.getYear()));
        allByMonth.forEach(workTime -> {
            long minutes = ChronoUnit.MINUTES.between(workTime.getStartTime(), workTime.getEndTime());
            long hours = ChronoUnit.HOURS.between(workTime.getStartTime(), workTime.getEndTime());

            String format = String.format("%02d.%02d %s - %s (%02d:%02d)",
                    workTime.getDayOfMonth(),
                    workTime.getMonthYear().getMonth(),
                    workTime.getStartTime().toString(),
                    workTime.getEndTime().toString(),
                    hours, minutes - (hours * 60));
            System.out.println(format);
        });
    }
}
