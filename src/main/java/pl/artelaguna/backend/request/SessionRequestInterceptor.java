package pl.artelaguna.backend.request;

import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pl.artelaguna.backend.account.model.Session;
import pl.artelaguna.backend.account.service.SessionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Stream;

@Component
@Log
public class SessionRequestInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SessionService sessions;

    @Autowired
    private RequestContext rc;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        val session = (request.getCookies() == null ? Stream.<Cookie>empty() : Arrays.stream(request.getCookies()))
                .filter(cookie -> cookie.getName().equals(Session.COOKIE_NAME))
                .findAny()
                .map(Cookie::getValue)
                .flatMap(sessions::getByToken);
        val currentThreadId = Thread.currentThread().getId();

        rc.sessionByThreadId.put(currentThreadId, session);
        rc.responseByThreadId.put(currentThreadId, response);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        val currentThreadId = Thread.currentThread().getId();

        rc.sessionByThreadId.remove(currentThreadId);
        rc.responseByThreadId.remove(currentThreadId);
    }
}
