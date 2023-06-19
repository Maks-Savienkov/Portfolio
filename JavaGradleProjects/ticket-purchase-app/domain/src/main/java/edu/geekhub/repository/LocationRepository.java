package edu.geekhub.repository;

import edu.geekhub.model.Location;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository {
    void insert(Location location);

    void delete(UUID id);

    Optional<Location> findById(UUID id);

    List<Location> getAll();
}
