package pl.artelaguna.backend.contest.repository;

import org.springframework.stereotype.Repository;
import pl.artelaguna.backend.contest.model.Artwork;
import pl.artelaguna.backend.util.FunRepository;

@Repository
public interface ArtworkRepository extends FunRepository<Artwork, Integer> {
}
