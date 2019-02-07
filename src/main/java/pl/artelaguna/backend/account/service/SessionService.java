package pl.artelaguna.backend.account.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.artelaguna.backend.account.model.Credentials;
import pl.artelaguna.backend.account.model.Session;
import pl.artelaguna.backend.account.repository.AccountRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    @Autowired
    private AccountRepository accounts;

    private final Map<String, Session> sessionByToken = new ConcurrentHashMap<>();

    public Optional<Session> create(Credentials credentials) {
        removeExpiredSessions();

        val newSession = accounts.findByEmail(credentials.getEmail())
                .filter(account -> account.getPassword().equals(credentials.getPassword()))
                .map(Session::new);

        newSession.ifPresent(session -> sessionByToken.put(session.getToken(), session));

        return newSession;
    }

    public Optional<Session> getByToken(String token) {
        removeExpiredSessions();

        return Optional.ofNullable(sessionByToken.get(token));
    }

    public boolean delete(String token) {
        removeExpiredSessions();

        return sessionByToken.remove(token) != null;
    }

    private void removeExpiredSessions() {
        sessionByToken.values().removeIf(Session::isExpired);
    }
}
