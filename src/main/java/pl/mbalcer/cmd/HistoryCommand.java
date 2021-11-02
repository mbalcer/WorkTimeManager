package pl.mbalcer.cmd;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;
import pl.mbalcer.repository.MonthYearRepository;
import pl.mbalcer.repository.WorkTimeRepository;
import pl.mbalcer.util.DateConstant;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Dependent
@Command(name = "history",
        mixinStandardHelpOptions = true,
        sortOptions = false,
        version = "Work Time Manager 1.0",
        header = "Save historical work time",
        description = "You can save the working time from another day",
        synopsisHeading = "%n",
        descriptionHeading = "%nDescription:%n",
        optionListHeading = "%nOptions:%n")
public class HistoryCommand implements Runnable {

    @Option(names = {"--date", "-d"}, required = true, description = "Date in format dd.MM.yyyy")
    String date;

    @Option(names = {"--start", "-s"}, description = "Start working time in format HH:mm")
    String start;

    @Option(names = {"--end", "-e"}, description = "End working time in format HH:mm")
    String end;

    @Inject
    MonthYearRepository monthYearRepository;

    @Inject
    WorkTimeRepository workTimeRepository;

    @Transactional
    @Override
    public void run() {
        LocalDate localDate = LocalDate.parse(date, DateConstant.DATE_FORMATTER);
        LocalTime startTime = null, endTime = null;
        if (start != null) {
            startTime = LocalTime.parse(start, DateConstant.TIME_FORMATTER);
        }
        if (end != null) {
            endTime = LocalTime.parse(end, DateConstant.TIME_FORMATTER);
        }

        MonthYear monthYear = monthYearRepository.findByMonthAndYear(localDate.getMonthValue(), localDate.getYear());
        WorkTime currentWorkTime = workTimeRepository.findByDate(localDate);
        if (currentWorkTime != null) {
            if (startTime != null) {
                currentWorkTime.setStartTime(startTime);
            }
            if (endTime != null) {
                currentWorkTime.setEndTime(endTime);
            }
        } else {
            LocalTime startTimeToSave = (startTime == null) ? DateConstant.START_WORK_DEFAULT : startTime;
            LocalTime endTimeToSave = (endTime == null) ? DateConstant.END_WORK_DEFAULT : endTime;
            currentWorkTime = new WorkTime(startTimeToSave, endTimeToSave, localDate.getDayOfMonth(), monthYear);
        }
        workTimeRepository.persist(currentWorkTime);
        log.info("Working time has been created: {}", currentWorkTime);
    }
}
