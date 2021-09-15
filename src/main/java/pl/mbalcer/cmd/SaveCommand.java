package pl.mbalcer.cmd;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Dependent
@Command(name = "save", mixinStandardHelpOptions = true)
public class SaveCommand implements Runnable {

    @Option(names = {"--start", "-s"})
    boolean start;

    @Transactional
    @Override
    public void run() {
        if (start) {
            LocalDateTime now = LocalDateTime.now();
            MonthYear monthYear = MonthYear.findByTodayDate(now);

            WorkTime workTime = new WorkTime(now.toLocalTime(), null, now.getDayOfMonth(), monthYear);
            workTime.persist();

            System.out.println(workTime);
        }
    }
}
