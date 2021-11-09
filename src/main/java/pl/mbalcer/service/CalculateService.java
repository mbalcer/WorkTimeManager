package pl.mbalcer.service;

import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;

import java.util.List;

public interface CalculateService {
    long calculateSumWorkingMinutes(List<WorkTime> workTimeList);

    long calculateWorkingMinutesInTheDay(WorkTime workTime);

    int calculateWorkingDays(MonthYear monthYear, int freeDays);
}
