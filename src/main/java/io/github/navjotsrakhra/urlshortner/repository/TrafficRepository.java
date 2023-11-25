package io.github.navjotsrakhra.urlshortner.repository;

import io.github.navjotsrakhra.urlshortner.data.model.Traffic;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TrafficRepository extends CrudRepository<Traffic, UUID> {

}
