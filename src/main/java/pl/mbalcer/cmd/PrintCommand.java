package pl.mbalcer.cmd;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Dependent
@Command(name = "print", mixinStandardHelpOptions = true)
public class PrintCommand implements Runnable {

    @Option(names = {"--month", "-m"})
    Integer month;

    @Transactional
    @Override
    public void run() {
        LocalDate today = LocalDate.now();
        if (month == null) {
            month = today.getMonthValue();
        }
        List<WorkTime> allByMonth = WorkTime.findAllByMonth(MonthYear.findByMonthAndYear(month, today.getYear()));
        allByMonth.forEach(workTime -> {
            String format = String.format("%d.%d %s - %s",
                    workTime.getDayOfMonth(),
                    workTime.getMonthYear().getMonth(),
                    workTime.getStartTime().toString(),
                    workTime.getEndTime().toString());
            System.out.println(format);
        });
    }
}
