package pl.artelaguna.backend.request;

import lombok.val;
import org.springframework.stereotype.Component;
import pl.artelaguna.backend.account.model.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestContext {

    final Map<Long, Optional<Session>> sessionByThreadId = new ConcurrentHashMap<>();
    final Map<Long, HttpServletResponse> responseByThreadId = new ConcurrentHashMap<>();

    public Optional<Session> getSession() {
        return sessionByThreadId.get(Thread.currentThread().getId());
    }

    public void setSession(Session session) {
        val sessionCookie = new Cookie(Session.COOKIE_NAME, session.getToken());
        sessionCookie.setPath("/");
        sessionCookie.setSecure(false);
        sessionCookie.setMaxAge((int) Session.DURATION.getSeconds());

        responseByThreadId.get(Thread.currentThread().getId()).addCookie(sessionCookie);
    }

    public void clearSession() {
        val sessionCookie = new Cookie(Session.COOKIE_NAME, "");
        sessionCookie.setPath("/");
        sessionCookie.setSecure(false);
        sessionCookie.setMaxAge(0);

        responseByThreadId.get(Thread.currentThread().getId()).addCookie(sessionCookie);
    }
}
