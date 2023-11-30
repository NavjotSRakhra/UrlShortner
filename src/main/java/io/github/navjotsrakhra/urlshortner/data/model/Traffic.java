package io.github.navjotsrakhra.urlshortner.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
public class Traffic {

    @SequenceGenerator(name = "traffic_sequence", sequenceName = "traffic_sequence", allocationSize = 1)
    @GeneratedValue(generator = "traffic_sequence")
    private @Id Long id;
    private @NotNull Long count;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "redirect_id")
    private Set<Redirect> redirects;

    public Traffic() {
        count = 0L;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void visited() {
        count++;
    }

    public Set<Redirect> getRedirects() {
        return redirects;
    }

    public void setRedirects(Set<Redirect> redirects) {
        this.redirects = redirects;
    }
}
