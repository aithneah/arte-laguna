package pl.artelaguna.backend.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.artelaguna.backend.account.model.Account;
import pl.artelaguna.backend.account.repository.AccountRepository;
import pl.artelaguna.backend.account.model.ChangePasswordForm;
import pl.artelaguna.backend.util.Optionals;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accounts;

    public Optional<Account> create(Account account) {
        return Optionals.invert(accounts.findByEmail(account.getEmail()), () -> account)
                .map(accounts::save);
    }

    public Optional<Account> getByEmail(String email) {
        return accounts.findByEmail(email);
    }

    public boolean changePassword(Account account, ChangePasswordForm changePasswordForm) {
        boolean oldPasswordMatches = account.getPassword().equals(changePasswordForm.getOldPassword());

        if (oldPasswordMatches) {
            account.setPassword(changePasswordForm.getNewPassword());
            accounts.save(account);
        }

        return oldPasswordMatches;
    }

    public Account update(Account account) {
        return accounts.save(account);
    }
}
