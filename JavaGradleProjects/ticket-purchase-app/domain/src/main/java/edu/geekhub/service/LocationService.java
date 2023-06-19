package edu.geekhub.service;

import edu.geekhub.exception.InvalidRequestException;
import edu.geekhub.exception.notfound.LocationNotFoundException;
import edu.geekhub.exception.validation.LocationValidationException;
import edu.geekhub.model.Location;
import edu.geekhub.repository.LocationRepository;
import edu.geekhub.validator.LocationValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository repository;
    private final LocationValidator validator;
    private final Logger logger = LoggerFactory.getLogger(LocationService.class);

    public Location create(
        String city,
        String country
    ) {
        Location location = new Location(
            UUID.randomUUID(),
            city,
            country
        );

        try {
            validator.validate(location);
            logger.info("Try to add a new location.");
            repository.insert(location);
            logger.info("Location added successfully.");
        } catch (LocationValidationException e) {
            logger.error("Failed to create location." + e.getMessage());
            throw new InvalidRequestException("Failed to create location. " + e.getMessage());
        }

        return location;
    }

    public void delete(UUID locationId) {
        logger.info("Deleting location.");
        repository.delete(locationId);
        logger.info("Location deleted successfully.");
    }

    public List<Location> getAll() {
        return repository.getAll();
    }

    public Location getById(UUID locationId) {
        return repository.findById(locationId).orElseThrow(() -> new LocationNotFoundException(locationId));
    }

    public boolean isExistsWithId(UUID id) {
        return repository.findById(id).isPresent();
    }
}
