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
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Traffic traffic;

    public Redirect(Instant now, String operatingSystem, Traffic traffic) {
        this.redirectTime = now;
        this.operatingSystem = operatingSystem;
        this.traffic = traffic;
    }

    public Redirect() {

    }

    public Instant getRedirectTime() {
        return redirectTime;
    }

    public void setRedirectTime(Instant redirectTime) {
        this.redirectTime = redirectTime;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Traffic getTraffic() {
        return traffic;
    }

    public void setTraffic(Traffic traffic) {
        this.traffic = traffic;
    }
}
