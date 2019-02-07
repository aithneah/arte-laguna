package pl.artelaguna.backend.contest.repository;

import org.springframework.stereotype.Repository;
import pl.artelaguna.backend.contest.model.Contest;
import pl.artelaguna.backend.contest.model.Contestant;
import pl.artelaguna.backend.contest.model.Entry;
import pl.artelaguna.backend.util.FunRepository;

import java.util.Optional;

@Repository
public interface EntryRepository extends FunRepository<Entry, Integer> {

    Optional<Entry> findByContestAndAuthor(Contest contest, Contestant author);
}
