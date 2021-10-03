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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Dependent
@Command(name = "history",
        mixinStandardHelpOptions = true,
        sortOptions = false,
        version = "Work Time Manager 1.0",
        header = "Save historical work time",
        description = "You can save the working time from another day",
        headerHeading = "Usage:%n%n",
        synopsisHeading = "%n",
        descriptionHeading = "%nDescription:%n",
        optionListHeading = "%nOptions:%n")
public class HistoryCommand implements Runnable {

    @Option(names = {"--date", "-d"}, required = true, description = "Date in format dd.MM.yyyy")
    String date;

    @Option(names = {"--start", "-s"}, defaultValue = "8:00", description = "Start working time in format HH:mm")
    String start;

    @Option(names = {"--end", "-e"}, defaultValue = "16:00", description = "End working time in format HH:mm")
    String end;

    @Inject
    MonthYearRepository monthYearRepository;

    @Inject
    WorkTimeRepository workTimeRepository;

    @Transactional
    @Override
    public void run() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:m");
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        LocalTime startTime = LocalTime.parse(start, timeFormatter);
        LocalTime endTime = LocalTime.parse(end, timeFormatter);

        MonthYear monthYear = monthYearRepository.findByMonthAndYear(localDate.getMonthValue(), localDate.getYear());
        WorkTime workTime = new WorkTime(startTime, endTime, localDate.getDayOfMonth(), monthYear);
        workTimeRepository.persist(workTime);
        log.info("Working time has been created: {}", workTime);
    }
}
