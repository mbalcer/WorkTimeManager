package pl.mbalcer.cmd;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.BreakTime;
import pl.mbalcer.model.WorkTime;
import pl.mbalcer.repository.BreakTimeRepository;
import pl.mbalcer.repository.WorkTimeRepository;
import pl.mbalcer.util.DateConstant;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

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
public class BreakTimeCommand implements Runnable {
    @Option(names = {"--date", "-d"})
    String date;

    @Option(names = {"--start", "-s"}, required = true)
    String start;

    @Option(names = {"--end", "-e"}, required = true)
    String end;

    @Inject
    BreakTimeRepository breakTimeRepository;

    @Inject
    WorkTimeRepository workTimeRepository;

    @Transactional
    @Override
    public void run() {
        LocalDate localDate = LocalDate.now();
        if (date != null) {
            localDate = LocalDate.parse(date, DateConstant.DATE_FORMATTER);
        }
        LocalTime startBreak = LocalTime.parse(start, DateConstant.TIME_FORMATTER);
        LocalTime endBreak = LocalTime.parse(end, DateConstant.TIME_FORMATTER);
        if (startBreak.isAfter(endBreak)) {
            throw new IllegalArgumentException("The start of the break must be before the end of the break");
        }

        WorkTime workTime = workTimeRepository.findByDate(localDate);
        if (workTime == null) {
            throw new IllegalArgumentException("You can define the break time after saving the working time for this date");
        }
        if (workTime.getStartTime().isAfter(startBreak) || workTime.getEndTime().isBefore(endBreak)) {
            throw new IllegalArgumentException("Break time must be during work");
        }
        BreakTime breakTime = new BreakTime(startBreak, endBreak, workTime);
        breakTimeRepository.persist(breakTime);
        log.info("Break has been created: {}", breakTime);
    }
}
