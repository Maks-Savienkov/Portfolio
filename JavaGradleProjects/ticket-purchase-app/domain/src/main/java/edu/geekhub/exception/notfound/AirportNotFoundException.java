package edu.geekhub.exception.notfound;

import java.util.UUID;

public class AirportNotFoundException extends EntityNotFoundException {
    public AirportNotFoundException(UUID id) {
        super("Airport with id '%s' not found".formatted(id.toString()));
    }
}
