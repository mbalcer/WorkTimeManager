package pl.mbalcer.service.impl;

import pl.mbalcer.model.MonthYear;
import pl.mbalcer.service.WorkingDaysService;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;

@ApplicationScoped
public class WorkingDaysServiceImpl implements WorkingDaysService {
    @Override
    public int calculate(MonthYear monthYear) {
        LocalDate firstDayInMonth = LocalDate.of(monthYear.getYear(), monthYear.getMonth(), 1);

        int workingDays = 0;
        for (int i = firstDayInMonth.getDayOfMonth(); i <= firstDayInMonth.lengthOfMonth(); i++) {
            LocalDate date = LocalDate.of(monthYear.getYear(), monthYear.getMonth(), i);
            int dayOfWeek = date.getDayOfWeek().getValue();
            if (dayOfWeek != 6 && dayOfWeek != 7) {
                workingDays++;
            }
        }

        return workingDays;
    }
}
