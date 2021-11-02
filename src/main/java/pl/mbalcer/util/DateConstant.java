package pl.mbalcer.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateConstant {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:m");
    public static final LocalTime START_WORK_DEFAULT = LocalTime.of(8, 0);
    public static final LocalTime END_WORK_DEFAULT = LocalTime.of(16, 0);
    public static final DateTimeFormatter REPORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final String REPORT_TIME_DEFAULT = "xx:xx";
}
