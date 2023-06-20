package edu.geekhub.exception.notfound;

import java.util.UUID;

public class TicketNotFoundException extends EntityNotFoundException {
    public TicketNotFoundException(UUID ticketId) {
        super("Ticket with id '%s' not found".formatted(ticketId.toString()));
    }
}
