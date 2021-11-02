package pl.mbalcer.cmd;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.Break;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;
import pl.mbalcer.repository.BreakRepository;
import pl.mbalcer.repository.MonthYearRepository;
import pl.mbalcer.repository.WorkTimeRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Dependent
@Command(name = "break",
        mixinStandardHelpOptions = true,
        sortOptions = false,
        version = "Work Time Manager 1.0",
        header = "",
        description = "",
        synopsisHeading = "%n",
        descriptionHeading = "%nDescription:%n",
        optionListHeading = "%nOptions:%n")
public class BreakCommand implements Runnable {
    @Option(names = {"--date", "-d"})
    String date;

    @Option(names = {"--start", "-s"})
    String start;

    @Option(names = {"--end", "-e"})
    String end;

    @Inject
    BreakRepository breakRepository;

    @Inject
    MonthYearRepository monthYearRepository;

    @Inject
    WorkTimeRepository workTimeRepository;

    @Override
    public void run() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        LocalTime startBreak = null, endBreak = null;
        if (start != null) {
            startBreak = LocalTime.parse(start, timeFormatter);
        }
        if (end != null) {
            endBreak = LocalTime.parse(end, timeFormatter);
        }

        MonthYear monthYear = monthYearRepository.findByMonthAndYear(localDate.getMonthValue(), localDate.getYear());
        WorkTime workTime = workTimeRepository.findByDate(localDate);
        Break newBreak = new Break(0l, startBreak, endBreak, workTime);
        breakRepository.persist(newBreak);
    }
}
