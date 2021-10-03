package pl.mbalcer.service.impl;

import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;
import pl.mbalcer.repository.WorkTimeRepository;
import pl.mbalcer.service.ReportService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReportServiceImpl implements ReportService {

    @Inject
    WorkTimeRepository workTimeRepository;

    @Override
    public String createMonthlyWorkTimeReport(MonthYear monthYear) {
        List<WorkTime> workTimeByMonth = workTimeRepository.findAllByMonth(monthYear);
        String workTimeReport = workTimeByMonth.stream()
                .sorted(Comparator.comparing(WorkTime::getDayOfMonth))
                .map(workTime -> {
                    long minutes = ChronoUnit.MINUTES.between(workTime.getStartTime(), workTime.getEndTime());
                    long hours = ChronoUnit.HOURS.between(workTime.getStartTime(), workTime.getEndTime());

                    return String.format("%02d.%02d %s - %s (%02d:%02d)",
                            workTime.getDayOfMonth(),
                            workTime.getMonthYear().getMonth(),
                            workTime.getStartTime().toString(),
                            workTime.getEndTime().toString(),
                            hours, minutes - (hours * 60));
                })
                .collect(Collectors.joining("\n"));

        return workTimeReport;
    }
}
