package pl.artelaguna.backend.contest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.artelaguna.backend.contest.model.Contest;
import pl.artelaguna.backend.contest.model.Contestant;
import pl.artelaguna.backend.contest.model.Entry;
import pl.artelaguna.backend.contest.repository.EntryRepository;
import pl.artelaguna.backend.util.Optionals;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class EntryService {

    @Autowired
    private EntryRepository entries;

    @Autowired
    private ContestService contests;

    public Optional<Entry> create(Contest contest, Contestant author) {
        return Optionals.invert(entries.findByContestAndAuthor(contest, author),
                () -> new Entry(0, contest, author, Collections.emptyList(), Collections.emptyList()))
                .map(entries::save);
    }

    public Optional<Entry> getOrCreateByContestant(Contestant author) {
        return contests.getCurrent()
                .map(contest -> entries.findByContestAndAuthor(contest, author)
                        .orElseGet(() -> entries.save(new Entry(0, contest, author, Collections.emptyList(), Collections.emptyList()))));
    }

    public Collection<Entry> getAll() {
        return entries.findAll();
    }

    public Optional<Entry> get(int id) {
        return entries.findOne(id);
    }
}
