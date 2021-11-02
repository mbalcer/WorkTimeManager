package pl.mbalcer.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pl.mbalcer.model.BreakTime;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BreakTimeRepository implements PanacheRepository<BreakTime> {
}
