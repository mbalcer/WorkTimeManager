package pl.mbalcer.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pl.mbalcer.model.Break;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BreakRepository implements PanacheRepository<Break> {
}
