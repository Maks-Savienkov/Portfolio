package edu.geekhub.exception.notfound;

import java.util.UUID;

public class FlightNotFoundException extends EntityNotFoundException {
    public FlightNotFoundException(UUID flightId) {
        super("Flight with id '%s' not found".formatted(flightId.toString()));
    }
}
