package pl.mbalcer.service.impl;

import pl.mbalcer.model.BreakTime;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;
import pl.mbalcer.repository.BreakTimeRepository;
import pl.mbalcer.repository.WorkTimeRepository;
import pl.mbalcer.service.ReportService;
import pl.mbalcer.util.DateConstant;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReportServiceImpl implements ReportService {
    private static final String DAY_REPORT_FORMAT = "%02d.%02d %s - %s [%02d:%02d]";
    private static final String ONE_BREAK_REPORT_FORMAT = "%s - %s";

    @Inject
    WorkTimeRepository workTimeRepository;

    @Inject
    BreakTimeRepository breakTimeRepository;

    @Override
    public String createMonthlyWorkTimeReport(MonthYear monthYear) {
        List<WorkTime> workTimeByMonth = workTimeRepository.findAllByMonth(monthYear);
        String workTimeReport = workTimeByMonth.stream()
                .sorted(Comparator.comparing(WorkTime::getDayOfMonth))
                .map(workTime -> {
                    long hours = 0, minutes = 0;
                    if (workTime.getStartTime() != null && workTime.getEndTime() != null) {
                        minutes = ChronoUnit.MINUTES.between(workTime.getStartTime(), workTime.getEndTime());
                        hours = ChronoUnit.HOURS.between(workTime.getStartTime(), workTime.getEndTime());
                    }

                    return String.format(DAY_REPORT_FORMAT,
                            workTime.getDayOfMonth(),
                            workTime.getMonthYear().getMonth(),
                            (workTime.getStartTime() != null) ? workTime.getStartTime().format(DateConstant.REPORT_TIME_FORMATTER) : DateConstant.REPORT_TIME_DEFAULT,
                            (workTime.getEndTime() != null) ? workTime.getEndTime().format(DateConstant.REPORT_TIME_FORMATTER) : DateConstant.REPORT_TIME_DEFAULT,
                            hours, minutes - (hours * 60))
                            + getBreaksInDayReport(workTime);
                })
                .collect(Collectors.joining("\n"));

        return workTimeReport;
    }

    private String getBreaksInDayReport(WorkTime workTime) {
        StringBuilder breaksInDayReport = new StringBuilder();
        List<BreakTime> breaks = breakTimeRepository.findByWorkTime(workTime);
        if (breaks.size() > 0) {
            breaksInDayReport.append(" (");
            breaksInDayReport.append(breaks.stream()
                    .map(breakTime -> String.format(ONE_BREAK_REPORT_FORMAT,
                                breakTime.getStartBreak().format(DateConstant.REPORT_TIME_FORMATTER),
                                breakTime.getEndBreak().format(DateConstant.REPORT_TIME_FORMATTER)))
                    .collect(Collectors.joining(", ")));
            breaksInDayReport.append(")");
        }
        return breaksInDayReport.toString();
    }
}
