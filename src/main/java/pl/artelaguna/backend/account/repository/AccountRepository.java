package pl.artelaguna.backend.account.repository;

import org.springframework.stereotype.Repository;
import pl.artelaguna.backend.account.model.Account;
import pl.artelaguna.backend.util.FunRepository;

import java.util.Optional;

@Repository
public interface AccountRepository extends FunRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);
}
