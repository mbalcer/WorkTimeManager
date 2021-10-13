package pl.mbalcer.cmd;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.WorkTime;
import pl.mbalcer.repository.WorkTimeRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Dependent
@Command(name = "save",
        mixinStandardHelpOptions = true,
        sortOptions = false,
        version = "Work Time Manager 1.0",
        header = "Save today's working time",
        description = "The command saves current work time to database. You can define start or end work time.",
        synopsisHeading = "%n",
        descriptionHeading = "%nDescription:%n",
        optionListHeading = "%nOptions:%n")
public class SaveCommand implements Runnable {

    @Option(names = {"--start", "-s"}, description = "Save start of working time")
    boolean start;

    @Option(names = {"--end", "-e"}, description = "Save end of working time")
    boolean end;

    @Inject
    WorkTimeRepository workTimeRepository;

    @Transactional
    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        WorkTime todayWorkTime = workTimeRepository.findTodayWorkTime(now);
        if (start) {
            if (todayWorkTime.getEndTime() != null && now.toLocalTime().isAfter(todayWorkTime.getEndTime())) {
                log.info("The start of the working time cannot be after the ending.");
                log.info("The end of the working time was cleared.");
                todayWorkTime.setEndTime(null);
            }
            todayWorkTime.setStartTime(now.toLocalTime());
        }
        if (end) {
            if (todayWorkTime.getStartTime() != null && now.toLocalTime().isBefore(todayWorkTime.getStartTime())) {
                log.info("The end of the working time cannot be before the starting.");
                log.info("The start of the working time was cleared.");
                todayWorkTime.setStartTime(null);
            }
            todayWorkTime.setEndTime(now.toLocalTime());
        }

        workTimeRepository.persist(todayWorkTime);
        log.info("Working time has been created/updated: {}", todayWorkTime);
    }
}
