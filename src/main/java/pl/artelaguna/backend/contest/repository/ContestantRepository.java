package pl.artelaguna.backend.contest.repository;

import org.springframework.stereotype.Repository;
import pl.artelaguna.backend.contest.model.Contestant;
import pl.artelaguna.backend.util.FunRepository;

@Repository
public interface ContestantRepository extends FunRepository<Contestant, Integer> {
}
