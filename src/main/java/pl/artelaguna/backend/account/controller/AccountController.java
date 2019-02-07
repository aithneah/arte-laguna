package pl.artelaguna.backend.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.artelaguna.backend.account.model.Credentials;
import pl.artelaguna.backend.account.service.AccountService;
import pl.artelaguna.backend.account.service.SessionService;
import pl.artelaguna.backend.account.model.ChangePasswordForm;
import pl.artelaguna.backend.request.RequestContext;
import pl.artelaguna.backend.util.Optionals;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("accounts")
public class AccountController {

    @Autowired
    private SessionService sessions;

    @Autowired
    private AccountService accounts;

    @Autowired
    private RequestContext rc;

    @GetMapping("login")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("login");
        mav.addObject("credentials", new Credentials());
        return mav;
    }

    @PostMapping("login")
    public ModelAndView login(@ModelAttribute Credentials credentials, HttpServletResponse response, ModelAndView mav) {
        Optionals.ifPresentOrElse(sessions.create(credentials),
                session -> {
                    rc.setSession(session);
                    mav.setViewName("redirect:/home");
                }, () -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    mav.setViewName("redirect:/accounts/login");
                });

        return mav;
    }

    @GetMapping("logout")
    public ModelAndView logout(ModelAndView mav) {
        rc.getSession().ifPresent(session -> {
                    sessions.delete(session.getToken());
                    rc.clearSession();
                });

        mav.setViewName("redirect:/home");

        return mav;
    }

    @GetMapping("password")
    public ModelAndView changePassword(ModelAndView mav) {
        Optionals.ifPresentOrElse(rc.getSession(),
                session -> {
                    switch (session.getAccount().getType()) {
                        case CONTESTANT:
                            mav.setViewName("password-change-contestant");
                            break;
                    }
                    mav.addObject("changePasswordForm", new ChangePasswordForm());
                }, () -> {
                    mav.setViewName("redirect:/home");
                });

        return mav;
    }

    @PostMapping("password")
    public ModelAndView changePassword(@ModelAttribute ChangePasswordForm changePasswordForm, ModelAndView mav) {
        Optionals.ifPresentOrElse(rc.getSession(),
                session -> {
                    if (accounts.changePassword(session.getAccount(), changePasswordForm))
                        mav.setViewName("redirect:/contestants/profile");
                    else
                        mav.setViewName("redirect:/accounts/password");
                }, () -> {
                    mav.setViewName("redirect:/home");
                });

        return mav;
    }
}
