package edu.geekhub.repository;

import edu.geekhub.model.dto.FlightPersistenceDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightRepository {
    void insert(FlightPersistenceDto flight);

    void delete(UUID id);

    void update(FlightPersistenceDto flight);

    Optional<FlightPersistenceDto> findById(UUID id);

    List<FlightPersistenceDto> getAll();

    List<FlightPersistenceDto> getAllByAirportId(UUID airportId);

    List<FlightPersistenceDto> getAllByAirportFromId(UUID airportFromId);

    List<FlightPersistenceDto> getAllByAirportToId(UUID airportToId);
}
