package pl.artelaguna.backend.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.artelaguna.backend.request.RequestContext;
import pl.artelaguna.backend.util.Optionals;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private RequestContext rc;

    @GetMapping({"/", "home"})
    public ModelAndView home(ModelAndView mav) {
        Optionals.ifPresentOrElse(rc.getSession(),
                session -> {
                    switch (session.getAccount().getType()) {
                        case CONTESTANT:
                            mav.setViewName("home-contestant");
                            break;
                        case JURY:
                            mav.setViewName("home-jury");
                            break;
                    }
                },
                () -> mav.setViewName("home"));

        return mav;
    }

    @GetMapping("about")
    public ModelAndView about(ModelAndView mav) {
        Optionals.ifPresentOrElse(rc.getSession(),
                session -> {
                    switch (session.getAccount().getType()) {
                        case CONTESTANT:
                            mav.setViewName("about-contestant");
                            break;
                        case JURY:
                            mav.setViewName("about-jury");
                            break;
                    }
                },
                () -> mav.setViewName("about"));

        return mav;
    }
}
