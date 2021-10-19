package pl.mbalcer.service;

import pl.mbalcer.model.MonthYear;

public interface WorkingDaysService {
    int calculate(MonthYear monthYear);
}
