package edu.geekhub.validator;

import edu.geekhub.exception.validation.FlightValidationException;
import edu.geekhub.model.Flight;
import edu.geekhub.model.FlightStatus;
import edu.geekhub.service.AirportService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.List;

@Component
public class FlightValidator {
    private final AirportService airportService;

    private final FlightValidationException exception;

    public FlightValidator(AirportService airportService) {
        this.airportService = airportService;
        this.exception = new FlightValidationException();
    }

    public void validate(Flight flight) {
        if (Objects.isNull(flight)) {
            exception.addMessage("Flight cannot be null.");
        } else {
            validateId(flight.getId());
            validateStatus(flight.getStatus());
            validateDateTime(flight.getDateTime());
            validateSeats(flight.getEconomySeats());
            validateSeats(flight.getBusinessSeats());
            validateSeats(flight.getFirstClassSeats());
            validateAirportId(flight.getAirportToId());
            validateAirportId(flight.getAirportFromId());
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

    private void validateStatus(FlightStatus status) {
        if (status != FlightStatus.SCHEDULED) {
            exception.addMessage("Just created flight can be SCHEDULED only. ");
        }
    }

    private void validateDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            exception.addMessage("DateTime cannot be null. ");
        } else {
            if (!dateTime.isBefore(LocalDateTime.now())) {
                exception.addMessage("DateTime cannot be before now.");
            }
        }
    }

    private void validateSeats(List<String> seats) {
        for (String seat : seats) {
            if (!seat.matches("^\\d+\\w$")) {
                exception.addMessage("Invalid format for seat %s. ".formatted(seat));
            }
        }
    }

    private void validateAirportId(UUID airportId) {
        if (airportId == null) {
            exception.addMessage("Airport ID cannot be null. ");
        } else {
            if (!airportService.isExistsWithId(airportId)) {
                exception.addMessage("Airport is not exist with id " + airportId + ". ");
            }
        }
    }
}
