package pl.artelaguna.backend.account.model;

import lombok.Value;
import lombok.val;

import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Value
public class Session implements Serializable {

    public static final Duration DURATION = Duration.ofMinutes(10);
    public static final String COOKIE_NAME = "session-token";

    private String token;
    private Instant expirationTime;
    private Account account;

    public Session(Account account) {
        token = UUID.randomUUID().toString();
        expirationTime = Instant.now().plus(DURATION);
        this.account = account;
    }

    public boolean isExpired() {
        return expirationTime.isBefore(Instant.now());
    }
}
