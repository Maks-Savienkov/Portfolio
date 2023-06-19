package edu.geekhub.validator;

import edu.geekhub.exception.validation.AirportValidationException;
import edu.geekhub.model.Airport;
import edu.geekhub.service.LocationService;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class AirportValidator {
    private final LocationService locationService;

    private final AirportValidationException exception;

    public AirportValidator(LocationService locationService) {
        this.locationService = locationService;
        this.exception = new AirportValidationException();
    }

    public void validate(Airport airport) {
        if (Objects.isNull(airport)) {
            exception.addMessage("Airport cannot be null.");
        } else {
            validateId(airport.getId());
            validateName(airport.getName());
            validateLocationId(airport.getLocationId());
        }

        if (!exception.isEmpty()) {
            throw exception;
        }
    }

    private void validateId(UUID id) {
        if (id == null) {
            exception.addMessage("Id cannot be null. ");
        }
    }

    private void validateName(String name) {
        if (name == null) {
            exception.addMessage("Name cannot be null. ");
        } else {
            if (name.isEmpty()) {
                exception.addMessage("Name cannot be empty. ");
            }
            if (!name.matches("^[A-Za-zА-ЩЬЮЯҐЄІЇа-щьюяґєії ]$")) {
                exception.addMessage("Name must contain only letters and spaces. ");
            }
        }
    }

    private void validateLocationId(UUID locationId) {
        if (locationId == null) {
            exception.addMessage("Location ID cannot be null. ");
        } else {
            if (!locationService.isExistsWithId(locationId)) {
                exception.addMessage("Location is not exist with id " + locationId + ". ");
            }
        }
    }
}
