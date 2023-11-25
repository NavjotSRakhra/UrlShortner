package io.github.navjotsrakhra.urlshortner.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Redirect {
    @GenericGenerator(name = "uuid", type = UuidGenerator.class)
    @GeneratedValue(strategy = GenerationType.UUID)
    private @Id UUID id;
    private @NotNull Instant redirectTime;
    private @NotNull String operatingSystem;
    @ManyToOne
    @JoinColumn(name = "traffic_id", nullable = false)
    private Traffic traffic;

    public Redirect(Instant now, String operatingSystem) {
        this.redirectTime = now;
        this.operatingSystem = operatingSystem;
    }

    public Redirect() {

    }
}
