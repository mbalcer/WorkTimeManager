package pl.mbalcer.service;

import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;

import java.util.List;

public interface CalculateService {
    long calculateSumWorkingMinutes(List<WorkTime> workTimeList);

    int calculateWorkingDays(MonthYear monthYear, int freeDays);
}
