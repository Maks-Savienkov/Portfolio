package edu.geekhub.repository;

import edu.geekhub.model.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository {
    void insert(Ticket ticket);

    void delete(UUID id);

    Optional<Ticket> findById(UUID id);

    List<Ticket> getAll();

    List<Ticket> getAllByUserId(UUID userId);

    List<Ticket> getAllByFlightId(UUID flightId);
}
