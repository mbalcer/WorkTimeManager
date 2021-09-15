package pl.mbalcer.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MonthYear extends PanacheEntity {

    private Integer month;
    private Integer year;

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
}
