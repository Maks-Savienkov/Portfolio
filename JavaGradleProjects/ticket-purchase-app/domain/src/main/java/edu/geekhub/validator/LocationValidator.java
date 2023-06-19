package edu.geekhub.validator;

import edu.geekhub.exception.validation.LocationValidationException;
import edu.geekhub.model.Location;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class LocationValidator {

    private final LocationValidationException exception;

    public LocationValidator() {
        this.exception = new LocationValidationException();
    }

    public void validate(Location location) {
        if (Objects.isNull(location)) {
            exception.addMessage("Location cannot be null.");
        } else {
            validateId(location.getId());
            validateCity(location.getCity());
            validateCountry(location.getCountry());
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

    private void validateCity(String city) {
        if (city == null) {
            exception.addMessage("City cannot be null. ");
        } else {
            if (city.isEmpty()) {
                exception.addMessage("City cannot be empty. ");
            }
            if (!city.matches("^[A-Za-z]+$")) {
                exception.addMessage("City must contain only letters. ");
            }
        }
    }

    private void validateCountry(String country) {
        if (country == null) {
            exception.addMessage("Country cannot be null. ");
        } else {
            if (country.isEmpty()) {
                exception.addMessage("Country cannot be empty. ");
            }
            if (!country.matches("^[A-Za-z]+$")) {
                exception.addMessage("Country must contain only letters. ");
            }
        }
    }
}
