package pl.artelaguna.backend.contest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.artelaguna.backend.contest.model.Artwork;
import pl.artelaguna.backend.contest.repository.ArtworkRepository;

import java.util.Optional;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworks;

    public Artwork create(Artwork artwork) {
        return artworks.save(artwork);
    }

    public Optional<Artwork> get(int id) {
        return artworks.findOne(id);
    }
}
