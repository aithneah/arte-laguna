package pl.artelaguna.backend.contest.repository;

import org.springframework.stereotype.Repository;
import pl.artelaguna.backend.contest.model.Contest;
import pl.artelaguna.backend.util.FunRepository;

@Repository
public interface ContestRepository extends FunRepository<Contest, Integer> {
}
