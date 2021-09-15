package pl.mbalcer.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class MonthYear extends PanacheEntity {

    private Integer month;

    private Integer year;

    public MonthYear(Integer month, Integer year) {
        this.month = month;
        this.year = year;
    }

    public MonthYear() {
    }

    public static MonthYear findByTodayDate(LocalDateTime now) {
        int nowMonth = now.getMonth().getValue();
        int nowYear = now.getYear();
        MonthYear monthYear = MonthYear.find("month = ?1 AND year = ?2", nowMonth, nowYear).firstResult();
        if (monthYear == null) {
            monthYear = new MonthYear(nowMonth, nowYear);
            monthYear.persist();
        }
        return monthYear;
    }

    @Override
    public String toString() {
        return "MonthYear{" +
                "month=" + month +
                ", year=" + year +
                '}';
    }
}
