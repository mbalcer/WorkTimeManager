package pl.mbalcer.service;

import pl.mbalcer.model.MonthYear;

public interface ReportService {
    String createMonthlyWorkTimeReport(MonthYear monthYear);
}
