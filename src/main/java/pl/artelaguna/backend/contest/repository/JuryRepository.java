package pl.artelaguna.backend.contest.repository;

import org.springframework.stereotype.Repository;
import pl.artelaguna.backend.contest.model.Jury;
import pl.artelaguna.backend.util.FunRepository;

@Repository
public interface JuryRepository extends FunRepository<Jury, Integer> {
}
