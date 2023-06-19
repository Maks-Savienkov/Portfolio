package edu.geekhub.service;

import edu.geekhub.exception.notfound.AirportNotFoundException;
import edu.geekhub.exception.validation.AirportValidationException;
import edu.geekhub.exception.InvalidRequestException;
import edu.geekhub.model.Airport;
import edu.geekhub.repository.AirportRepository;
import edu.geekhub.validator.AirportValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository repository;
    private final AirportValidator validator;
    private final Logger logger = LoggerFactory.getLogger(AirportService.class);

    public Airport create(
        String name,
        UUID locationId
    ) {
        Airport airport = new Airport(
            UUID.randomUUID(),
            name,
            locationId
        );

        try {
            validator.validate(airport);
            logger.info("Try to add a new airport.");
            repository.insert(airport);
            logger.info("Airport added successfully.");
        } catch (AirportValidationException e) {
            logger.error("Failed to create airport." + e.getMessage());
            throw new InvalidRequestException("Failed to create airport. " + e.getMessage());
        }

        return airport;
    }

    public void delete(UUID airportId) {
        logger.info("Deleting airport.");
        repository.delete(airportId);
        logger.info("Airport deleted successfully.");
    }

    public List<Airport> getAll() {
        return repository.getAll();
    }

    public Airport getById(UUID airportId) {
        return repository.findById(airportId).orElseThrow(() -> new AirportNotFoundException(airportId));
    }

    public List<Airport> getAllByLocationId(UUID locationId) {
        return repository.getAllByLocationId(locationId);
    }

    public boolean isExistsWithId(UUID id) {
        return repository.findById(id).isPresent();
    }
}
