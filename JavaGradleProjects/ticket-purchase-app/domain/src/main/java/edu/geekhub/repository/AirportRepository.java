package edu.geekhub.repository;

import edu.geekhub.model.Airport;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AirportRepository {
    void insert(Airport airport);

    void delete(UUID id);

    Optional<Airport> findById(UUID id);

    List<Airport> getAll();

    List<Airport> getAllByLocationId(UUID locationId);
}
