package pl.artelaguna.backend.contest.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.artelaguna.backend.contest.model.Entry;
import pl.artelaguna.backend.contest.model.Jury;
import pl.artelaguna.backend.contest.model.Vote;
import pl.artelaguna.backend.contest.repository.VoteRepository;

@Service
public class VoteService {

    @Autowired
    private VoteRepository votes;

    @Autowired
    private EntryService entries;

    public void vote(Jury jury, Entry entry, int rating) {
        val vote = votes.getByEntryAndJury(entry, jury)
                .map(v -> {
                    v.setRating(rating);
                    return v;
                }).orElseGet(() -> new Vote(0, entry, jury, rating));

        votes.save(vote);
    }
}
