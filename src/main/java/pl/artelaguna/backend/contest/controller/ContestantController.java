package pl.artelaguna.backend.contest.controller;

import lombok.extern.java.Log;
import lombok.val;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.artelaguna.backend.account.model.Account;
import pl.artelaguna.backend.account.model.Credentials;
import pl.artelaguna.backend.account.model.Session;
import pl.artelaguna.backend.contest.model.Contestant;
import pl.artelaguna.backend.contest.model.Entry;
import pl.artelaguna.backend.contest.service.ContestantService;
import pl.artelaguna.backend.request.RequestContext;
import pl.artelaguna.backend.util.Optionals;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Log
@RestController
@RequestMapping("contestants")
public class ContestantController {

    @Autowired
    private ContestantService contestants;

    @Autowired
    private RequestContext rc;

    @GetMapping("apply")
    public ModelAndView create(ModelAndView mav) {
        val emptyContestant = new Contestant();
        emptyContestant.setAccount(new Account());

        mav.setViewName("apply");
        mav.addObject("contestant", emptyContestant);
        return mav;
    }

    @PostMapping("apply")
    public ModelAndView create(@ModelAttribute Contestant contestant, ModelAndView mav, HttpServletResponse response) {
        Optionals.ifPresentOrElse(contestants.create(contestant),
                c -> {
                    mav.setViewName("login");
                    mav.addObject("credentials", new Credentials(c.getAccount().getEmail(), ""));
                }, () -> {
                    mav.setViewName("apply");
                });

        return mav;
    }

    @GetMapping("profile")
    public ModelAndView viewProfile(ModelAndView mav) {
        Optionals.ifPresentOrElse(rc.getSession(),
                session -> {
                    switch (session.getAccount().getType()) {
                        case CONTESTANT:
                            mav.setViewName("profile-view-contestant");
                            contestants.get(session.getAccount().getId())
                                    .ifPresent(contestant -> mav.addObject("contestant", contestant));
                            break;
                    }
                }, () -> {
                    mav.setViewName("redirect:/home");
                });

        return mav;
    }

    @GetMapping("profile/edit")
    public ModelAndView editProfile(ModelAndView mav) {
        Optionals.ifPresentOrElse(rc.getSession(),
                session -> {
                    switch (session.getAccount().getType()) {
                        case CONTESTANT:
                            mav.setViewName("profile-edit-contestant");
                            contestants.get(session.getAccount().getId())
                                    .ifPresent(contestant -> mav.addObject("contestant", contestant));
                            break;
                    }
                }, () -> {
                    mav.setViewName("redirect:/home");
                });

        return mav;
    }

    @PostMapping("profile/edit")
    public ModelAndView editProfile(@ModelAttribute Contestant editedContestant, ModelAndView mav) {
        Optionals.ifPresentOrElse(rc.getSession(),
                session -> {
                    switch (session.getAccount().getType()) {
                        case CONTESTANT:
                            mav.setViewName("profile-view-contestant");
                            contestants.get(session.getAccount().getId())
                                    .ifPresent(contestant -> {
                                        mav.addObject("contestant", contestant);
                                        contestant.getAccount().setEmail(editedContestant.getAccount().getEmail());
                                        contestant.setName(editedContestant.getName());
                                        contestant.setSurname(editedContestant.getSurname());
                                        contestant.setAddress(editedContestant.getAddress());
                                        contestant.setPlaceOfBirth(editedContestant.getPlaceOfBirth());
                                        contestant.setDateOfBirth(editedContestant.getDateOfBirth());
                                        contestant.setPhoneNumber(editedContestant.getPhoneNumber());
                                        contestant.setWebsite(editedContestant.getWebsite());
                                        contestants.update(contestant);
                                    });
                            break;
                    }
                }, () -> {
                    mav.setViewName("redirect:/home");
                });

        return mav;
    }
}
