package edu.geekhub.service;

import edu.geekhub.exception.FlightStatusException;
import edu.geekhub.exception.InvalidRequestException;
import edu.geekhub.exception.notfound.FlightNotFoundException;
import edu.geekhub.exception.validation.FlightValidationException;
import edu.geekhub.mapper.FlightMapper;
import edu.geekhub.model.Flight;
import edu.geekhub.model.FlightAirportDirection;
import edu.geekhub.model.FlightStatus;
import edu.geekhub.model.Ticket;
import edu.geekhub.model.TravelClass;
import edu.geekhub.model.dto.FlightPersistenceDto;
import edu.geekhub.repository.FlightRepository;
import edu.geekhub.validator.FlightValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository repository;
    private final FlightValidator validator;
    private final FlightMapper mapper;
    private final TicketService ticketService;
    private final Logger logger = LoggerFactory.getLogger(FlightService.class);

    public Flight create(Flight flight) {
        flight.setId(UUID.randomUUID());
        flight.setStatus(FlightStatus.SCHEDULED);

        try {
            validator.validate(flight);
            logger.info("Try to add a new flight.");
            repository.insert(mapper.toDto(flight));
            logger.info("Flight added successfully.");
        } catch (FlightValidationException e) {
            logger.error("Failed to create flight." + e.getMessage());
            throw new InvalidRequestException("Failed to create flight. " + e.getMessage());
        }

        return flight;
    }

    public void delete(UUID flightId) {
        logger.info("Deleting flight.");
        repository.delete(flightId);
        logger.info("Flight deleted successfully.");
    }

    public Flight changeStatus(UUID flightId, FlightStatus toStatus) {
        final FlightPersistenceDto flightDto = repository.findById(flightId)
            .orElseThrow(() -> new FlightNotFoundException(flightId));
        if (!flightDto.getStatus().canChangeTo(toStatus)) {
            throw new FlightStatusException(
                "State transition '%s' -> '%s' isn't supported".formatted(flightDto.getStatus(), toStatus)
            );
        }
        flightDto.setStatus(toStatus);
        repository.update(flightDto);

        return mapper.toModel(flightDto);
    }

    public List<Flight> getAll() {
        return repository.getAll().stream()
            .map(mapper::toModel)
            .toList();
    }

    public Flight getById(UUID flightId) {
         return mapper.toModel(
             repository.findById(flightId)
                 .orElseThrow(() -> new FlightNotFoundException(flightId))
         );
    }

    public List<Flight> getAllByAirportId(UUID airportId, FlightAirportDirection direction) {
        return switch (direction) {
            case ANY -> repository.getAllByAirportId(airportId).stream()
                .map(mapper::toModel)
                .toList();
            case FROM -> repository.getAllByAirportFromId(airportId).stream()
                .map(mapper::toModel)
                .toList();
            case TO -> repository.getAllByAirportToId(airportId).stream()
                .map(mapper::toModel)
                .toList();
        };
    }

    public boolean isExistsFlightWithId(UUID flightId) {
        return repository.findById(flightId).isPresent();
    }

    public boolean isExistsSeat(String seat, TravelClass travelClass, UUID flightId) {
        Optional<FlightPersistenceDto> optionalFlight = repository.findById(flightId);
        if (optionalFlight.isPresent()) {
            switch (travelClass) {
                case FIRST -> {
                    return optionalFlight.get().getFirstClassSeats().contains(seat);
                }
                case ECONOMY -> {
                    return optionalFlight.get().getEconomySeats().contains(seat);
                }
                case BUSINESS -> {
                    return optionalFlight.get().getBusinessSeats().contains(seat);
                }
                default -> {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isOccupiedSeat(String seat, TravelClass travelClass, UUID flightId) {
        List<Ticket> tickets = ticketService.getAllByFlightId(flightId);
        for (Ticket ticket : tickets) {
            if (Objects.equals(ticket.getSeat(), seat) && ticket.getTravelClass() == travelClass) {
                return true;
            }
        }
        return false;
    }
}
