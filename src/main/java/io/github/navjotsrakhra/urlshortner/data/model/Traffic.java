package io.github.navjotsrakhra.urlshortner.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Set;

@Entity
public class Traffic {

    @SequenceGenerator(name = "traffic_sequence", sequenceName = "traffic_sequence", allocationSize = 1)
    @GeneratedValue(generator = "traffic_sequence")
    private @Id Long id;
    private @NotNull Long count;

    @OneToMany(mappedBy = "traffic")
    private Set<Redirect> redirects;

    public Traffic() {
        count = 0L;
    }

    public Long getCount() {
        return count;
    }

    public void visited(String operatingSystem) {
        count++;
        redirects.add(new Redirect(Instant.now(), operatingSystem));
    }
}
