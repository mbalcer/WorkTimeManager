package pl.mbalcer.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pl.mbalcer.model.MonthYear;
import pl.mbalcer.model.WorkTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class WorkTimeRepository implements PanacheRepository<WorkTime> {

    @Inject
    MonthYearRepository monthYearRepository;

    public WorkTime findTodayWorkTime(LocalDateTime now) {
        WorkTime todayWorkTime = find("dayOfMonth", now.getDayOfMonth()).firstResult();
        if (todayWorkTime == null) {
            todayWorkTime = new WorkTime(null, null, now.getDayOfMonth(), monthYearRepository.findByTodayDate(now));
        }
        return todayWorkTime;
    }

    public List<WorkTime> findAllByMonth(MonthYear monthYear) {
        return find("monthYear", monthYear).list();
    }
}
