package pl.artelaguna.backend.contest.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.artelaguna.backend.account.model.AccountType;
import pl.artelaguna.backend.account.service.AccountService;
import pl.artelaguna.backend.contest.model.Jury;
import pl.artelaguna.backend.contest.repository.JuryRepository;

import java.util.Optional;

@Service
@Log
public class JuryService {

    @Autowired
    private AccountService accounts;

    @Autowired
    private JuryRepository juries;

    public Optional<Jury> get(int id) {
        return juries.findOne(id);
    }


}
