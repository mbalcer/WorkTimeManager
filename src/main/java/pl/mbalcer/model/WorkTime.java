package pl.mbalcer.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkTime extends PanacheEntity {
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer dayOfMonth;

    @ManyToOne
    private MonthYear monthYear;

    public static WorkTime findTodayWorkTime(LocalDateTime now) {
        WorkTime todayWorkTime = WorkTime.find("dayOfMonth", now.getDayOfMonth()).firstResult();
        if (todayWorkTime == null) {
            todayWorkTime = new WorkTime(null, null, now.getDayOfMonth(), MonthYear.findByTodayDate(now));
        }
        return todayWorkTime;
    }

    public static List<WorkTime> findAllByMonth(MonthYear monthYear) {
        return WorkTime.find("monthYear", monthYear).list();
    }
}
