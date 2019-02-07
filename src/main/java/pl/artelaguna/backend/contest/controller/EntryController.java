package pl.artelaguna.backend.contest.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pl.artelaguna.backend.account.model.Account;
import pl.artelaguna.backend.account.model.Session;
import pl.artelaguna.backend.contest.model.Artwork;
import pl.artelaguna.backend.contest.service.*;
import pl.artelaguna.backend.request.RequestContext;
import pl.artelaguna.backend.util.Optionals;

import java.io.IOException;

@RestController
@RequestMapping("entries")
public class EntryController {

    @Autowired
    private ContestantService contestants;

    @Autowired
    private JuryService juries;

    @Autowired
    private EntryService entries;

    @Autowired
    private ArtworkService artworks;

    @Autowired
    private VoteService votes;

    @Autowired
    private RequestContext rc;

    @GetMapping("view")
    public ModelAndView viewEntry(ModelAndView mav) {
        val account = rc.getSession()
                .map(Session::getAccount);

        Optionals.ifPresentOrElse(account,
                a -> {
                    switch (a.getType()) {
                        case CONTESTANT:
                            val contestantEntry = contestants.get(a.getId())
                                    .flatMap(entries::getOrCreateByContestant);

                            mav.setViewName("entry-view-contestant");
                            Optionals.ifPresentOrElse(contestantEntry,
                                    entry -> {
                                        mav.addObject("entry", entry);
                                        mav.addObject("newArtwork", new Artwork());
                                    }, () -> {});
                            break;
                        case JURY:
                            mav.setViewName("entry-view-judge");
                            mav.addObject("entries", entries.getAll());
                            break;
                    }
                }, () -> mav.setViewName("redirect:/home"));

        return mav;
    }

    @GetMapping("view/{id}")
    public ModelAndView viewEntryById(@PathVariable int id, ModelAndView mav) {
        val jury = rc.getSession()
                .map(Session::getAccount)
                .map(Account::getId)
                .flatMap(juries::get);
        val entry = entries.get(id);

        Optionals.ifPresentOrElse(jury, entry,
                (j, e) -> {
                    mav.setViewName("artwork-view-judge");
                    mav.addObject("entry", e);
                }, () -> mav.setViewName("redirect:/home"));

        return mav;
    }

    @PostMapping("view")
    public ModelAndView viewEntry(@ModelAttribute Artwork newArtwork, @RequestParam("newArtworkFile") MultipartFile newArtworkFile, ModelAndView mav) {
        try {
            newArtwork.setImage(newArtworkFile.getBytes());
            rc.getSession()
                    .map(Session::getAccount)
                    .map(Account::getId)
                    .flatMap(contestants::get)
                    .flatMap(entries::getOrCreateByContestant)
                    .ifPresent(newArtwork::setEntry);
            artworks.create(newArtwork);

            mav.setViewName("redirect:/entries/view");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mav;
    }

    @PostMapping("{id}/rate")
    public ModelAndView rateEntry(@PathVariable int id, @RequestParam("rating") int rating, ModelAndView mav) {
        val jury = rc.getSession()
                .map(Session::getAccount)
                .map(Account::getId)
                .flatMap(juries::get);
        val entry = entries.get(id);

        Optionals.ifPresentOrElse(jury, entry,
                (j, e) -> {
                    votes.vote(j, e, rating);

                    mav.setViewName("redirect:/entries/view");
                }, () -> mav.setViewName("redirect:/home"));

        return mav;
    }
}
