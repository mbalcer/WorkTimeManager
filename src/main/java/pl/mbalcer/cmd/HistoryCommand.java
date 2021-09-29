package pl.mbalcer.cmd;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Dependent
@Command(name = "history", mixinStandardHelpOptions = true)
public class HistoryCommand implements Runnable {

    @Option(names = {"--date", "-d"}, required = true)
    String date;

    @Option(names = {"--start", "-s"})
    String start;

    @Option(names = {"--end", "-e"})
    String end;

    @Transactional
    @Override
    public void run() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        LocalTime startTime = LocalTime.parse(start, timeFormatter);
        LocalTime endTime = LocalTime.parse(end, timeFormatter);

        MonthYear monthYear = MonthYear.findByMonthAndYear(localDate.getMonthValue(), localDate.getYear());
        WorkTime workTime = new WorkTime(startTime, endTime, localDate.getDayOfMonth(), monthYear);
        workTime.persist();
        System.out.println(workTime);
    }
}
