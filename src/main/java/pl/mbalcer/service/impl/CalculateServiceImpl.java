package pl.mbalcer.service.impl;

import pl.mbalcer.model.BreakTime;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;
import pl.mbalcer.repository.BreakTimeRepository;
import pl.mbalcer.service.CalculateService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@ApplicationScoped
public class CalculateServiceImpl implements CalculateService {
    @Inject
    BreakTimeRepository breakTimeRepository;

    @Override
    public long calculateSumWorkingMinutes(List<WorkTime> workTimeList) {
        return workTimeList.stream()
                .filter(workTime -> workTime.getStartTime() != null && workTime.getEndTime() != null)
                .mapToLong(workTime -> calculateWorkingMinutesInTheDay(workTime))
                .sum();
    }

    @Override
    public long calculateWorkingMinutesInTheDay(WorkTime workTime) {
        List<BreakTime> breaksInDay = breakTimeRepository.findByWorkTime(workTime);
        long sumBreaks = 0;
        if (breaksInDay.size() > 0) {
            sumBreaks = breaksInDay.stream()
                    .mapToLong(breakTime -> ChronoUnit.MINUTES.between(breakTime.getStartBreak(), breakTime.getEndBreak()))
                    .sum();
        }

        return ChronoUnit.MINUTES.between(workTime.getStartTime(), workTime.getEndTime()) - sumBreaks;
    }

    @Override
    public int calculateWorkingDays(MonthYear monthYear, int freeDays) {
        LocalDate firstDayInMonth = LocalDate.of(monthYear.getYear(), monthYear.getMonth(), 1);

        int workingDays = 0;
        for (int i = firstDayInMonth.getDayOfMonth(); i <= firstDayInMonth.lengthOfMonth(); i++) {
            LocalDate date = LocalDate.of(monthYear.getYear(), monthYear.getMonth(), i);
            int dayOfWeek = date.getDayOfWeek().getValue();
            if (dayOfWeek != 6 && dayOfWeek != 7) {
                workingDays++;
            }
        }

        return workingDays - freeDays;
    }
}
