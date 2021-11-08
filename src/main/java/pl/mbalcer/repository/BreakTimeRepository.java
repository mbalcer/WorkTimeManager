package pl.mbalcer.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pl.mbalcer.model.BreakTime;
import pl.mbalcer.model.WorkTime;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class BreakTimeRepository implements PanacheRepository<BreakTime> {
    public List<BreakTime> findByWorkTime(WorkTime workTime) {
        return find("workTime", workTime).list();
    }
}
