package pl.artelaguna.backend.contest.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.artelaguna.backend.account.model.AccountType;
import pl.artelaguna.backend.account.service.AccountService;
import pl.artelaguna.backend.contest.model.Contestant;
import pl.artelaguna.backend.contest.repository.ContestantRepository;

import java.util.Optional;

@Service
@Log
public class ContestantService {

    @Autowired
    private AccountService accounts;

    @Autowired
    private ContestantRepository contestants;

    public Optional<Contestant> get(int id) {
        return contestants.findOne(id);
    }

    public Optional<Contestant> create(Contestant contestant) {
        contestant.getAccount().setType(AccountType.CONTESTANT);

        return accounts.create(contestant.getAccount())
                .map(account -> contestant)
                .map(contestants::save);
    }

    public Contestant update(Contestant contestant) {
        accounts.update(contestant.getAccount());
        return contestants.save(contestant);
    }
}
