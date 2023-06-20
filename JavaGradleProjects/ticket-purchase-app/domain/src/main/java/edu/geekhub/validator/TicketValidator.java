package edu.geekhub.validator;

import edu.geekhub.exception.validation.TicketValidationException;
import edu.geekhub.model.Ticket;
import edu.geekhub.model.TravelClass;
import edu.geekhub.service.FlightService;
import edu.geekhub.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class TicketValidator {
    private final UserService userService;
    private final FlightService flightService;

    private final TicketValidationException exception;

    public TicketValidator(UserService userService, FlightService flightService) {
        this.userService = userService;
        this.flightService = flightService;
        this.exception = new TicketValidationException();
    }

    public void validate(Ticket ticket) {
        if (Objects.isNull(ticket)) {
            exception.addMessage("Ticket cannot be null.");
        } else {
            validateId(ticket.getId());
            validateFlightId(ticket.getFlightId());
            validateUserId(ticket.getUserId());
            validateClass(ticket.getTravelClass());
            validateSeat(ticket.getSeat(), ticket.getTravelClass(), ticket.getFlightId());
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

    private void validateFlightId(UUID flightId) {
        if (Objects.isNull(flightId)) {
            exception.addMessage("Flight ID cannot be null. ");
        }
        if (!flightService.isExistsFlightWithId(flightId)) {
            exception.addMessage("Flight with id %s is not exists. ".formatted(flightId.toString()));
        }
    }

    private void validateUserId(UUID userId) {
        if (Objects.isNull(userId)) {
            exception.addMessage("User ID cannot be null. ");
        }
        if (!userService.isExistsUserWithId(userId)) {
            exception.addMessage("User with id %s is not exists. ".formatted(userId.toString()));
        }
    }

    private void validateClass(TravelClass travelClass) {
        if (Objects.isNull(travelClass)) {
            exception.addMessage("Travel class cannot be null. ");
        }
    }

    private void validateSeat(String seat, TravelClass travelClass, UUID flightId) {
        if (Objects.isNull(seat)) {
            exception.addMessage("Seat cannot be null. ");
        }
        if (!flightService.isExistsSeat(seat, travelClass, flightId)) {
            exception.addMessage("Seat %s is not exists for this flight. ".formatted(seat));
        } else {
            if (flightService.isOccupiedSeat(seat, travelClass, flightId)) {
                exception.addMessage("Seat %s is already occupied. ".formatted(seat));
            }
        }
    }
}
