package pl.mbalcer.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalTime;

@Entity
public class WorkTime extends PanacheEntity {
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer dayOfMonth;

    @ManyToOne
    private MonthYear monthYear;

    public WorkTime(LocalTime startTime, LocalTime endTime, Integer dayOfMonth, MonthYear monthYear) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfMonth = dayOfMonth;
        this.monthYear = monthYear;
    }

    public WorkTime() {
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public MonthYear getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(MonthYear monthYear) {
        this.monthYear = monthYear;
    }

    @Override
    public String toString() {
        return "WorkTime{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", dayOfMonth=" + dayOfMonth +
                ", monthYear=" + monthYear +
                '}';
    }
}
