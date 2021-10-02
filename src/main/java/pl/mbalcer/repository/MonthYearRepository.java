package pl.mbalcer.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pl.mbalcer.model.MonthYear;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

@ApplicationScoped
public class MonthYearRepository implements PanacheRepository<MonthYear> {

    public MonthYear findByTodayDate(LocalDateTime now) {
        int nowMonth = now.getMonth().getValue();
        int nowYear = now.getYear();
        return findByMonthAndYear(nowMonth, nowYear);
    }

    public MonthYear findByMonthAndYear(int month, int year) {
        MonthYear monthYear = find("month = ?1 AND year = ?2", month, year).firstResult();
        if (monthYear == null) {
            monthYear = new MonthYear(month, year);
            persist(monthYear);
        }
        return monthYear;
    }
}
