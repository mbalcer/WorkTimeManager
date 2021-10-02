package pl.mbalcer.cmd;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import pl.mbalcer.model.WorkTime;
import pl.mbalcer.repository.WorkTimeRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Dependent
@Command(name = "save", mixinStandardHelpOptions = true)
public class SaveCommand implements Runnable {

    @Option(names = {"--start", "-s"})
    boolean start;

    @Option(names = {"--end", "-e"})
    boolean end;

    @Inject
    WorkTimeRepository workTimeRepository;

    @Transactional
    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        WorkTime todayWorkTime = workTimeRepository.findTodayWorkTime(now);
        if (start) {
            todayWorkTime.setStartTime(now.toLocalTime());
        }
        if (end) {
            todayWorkTime.setEndTime(now.toLocalTime());
        }

        workTimeRepository.persist(todayWorkTime);
        System.out.println(todayWorkTime);
    }
}
