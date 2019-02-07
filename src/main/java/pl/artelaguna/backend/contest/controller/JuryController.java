package pl.artelaguna.backend.contest.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pl.artelaguna.backend.contest.service.JuryService;
import pl.artelaguna.backend.request.RequestContext;
import pl.artelaguna.backend.util.Optionals;

@Log
@RestController
@RequestMapping("jury")
public class JuryController {

    @Autowired
    private JuryService juries;

    @Autowired
    private RequestContext rc;

    @GetMapping("profile")
    public ModelAndView viewProfile(ModelAndView mav) {
        Optionals.ifPresentOrElse(rc.getSession(),
                session -> {
                    switch (session.getAccount().getType()) {
                        case JURY:
                            mav.setViewName("profile-view-jury");
                            juries.get(session.getAccount().getId())
                                    .ifPresent(jury -> mav.addObject("jury", jury));
                            break;
                    }
                }, () -> {
                    mav.setViewName("redirect:/home");
                });

        return mav;
    }
}
