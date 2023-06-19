package edu.geekhub.exception.notfound;

import java.util.UUID;

public class LocationNotFoundException extends EntityNotFoundException {
    public LocationNotFoundException(UUID locationId) {
        super("Location with id '%s' not found".formatted(locationId.toString()));
    }
}
