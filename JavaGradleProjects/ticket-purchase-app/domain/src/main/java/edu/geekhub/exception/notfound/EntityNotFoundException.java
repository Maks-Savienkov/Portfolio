package edu.geekhub.exception.notfound;

import edu.geekhub.exception.TicketPurchaseAppException;

public class EntityNotFoundException extends TicketPurchaseAppException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
