package pl.artelaguna.backend.contest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    private Contest contest;

    @ManyToOne(optional = false)
    private Contestant author;

    @OneToMany(mappedBy = "entry")
    private Collection<Artwork> artworks;

    @OneToMany(mappedBy = "entry")
    private Collection<Vote> votes;

    public double getAverageRating() {
        return (int) (votes.stream()
                .mapToDouble(Vote::getRating)
                .average()
                .orElse(0) * 100) / 100.0;
    }
}
