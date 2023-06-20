package edu.geekhub.service;

import edu.geekhub.exception.InvalidRequestException;
import edu.geekhub.exception.notfound.TicketNotFoundException;
import edu.geekhub.exception.validation.FlightValidationException;
import edu.geekhub.model.Ticket;
import edu.geekhub.model.TravelClass;
import edu.geekhub.repository.TicketRepository;
import edu.geekhub.validator.TicketValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository repository;
    private final TicketValidator validator;
    private final Logger logger = LoggerFactory.getLogger(TicketService.class);

    public Ticket create(
        String seat,
        TravelClass travelClass,
        UUID userId,
        UUID flightId
    ) {
        Ticket ticket = new Ticket(
            UUID.randomUUID(),
            seat,
            travelClass,
            userId,
            flightId
        );

        try {
            validator.validate(ticket);
            logger.info("Try to add a new ticket.");
            repository.insert(ticket);
            logger.info("Ticket added successfully.");
        } catch (FlightValidationException e) {
            logger.error("Failed to create ticket." + e.getMessage());
            throw new InvalidRequestException("Failed to create ticket. " + e.getMessage());
        }

        return ticket;
    }

    public void delete(UUID ticketId) {
        logger.info("Deleting ticket.");
        repository.delete(ticketId);
        logger.info("Ticket deleted successfully.");
    }

    public List<Ticket> getAll() {
        return repository.getAll();
    }

    public Ticket getById(UUID ticketId) {
        return repository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException(ticketId));
    }

    public List<Ticket> getAllByUserId(UUID userId) {
        return repository.getAllByUserId(userId);
    }

    public List<Ticket> getAllByFlightId(UUID flightId) {
        return repository.getAllByFlightId(flightId);
    }
}
