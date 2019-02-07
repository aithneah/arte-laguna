package pl.artelaguna.backend.contest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.artelaguna.backend.contest.model.Contest;
import pl.artelaguna.backend.contest.repository.ContestRepository;

import java.util.Optional;

@Service
public class ContestService {

    @Autowired
    private ContestRepository contests;

    public Optional<Contest> getCurrent() {
        return contests.findAll().stream()
                .filter(contest -> contest.getStatus().isActive())
                .findAny();
    }
}
