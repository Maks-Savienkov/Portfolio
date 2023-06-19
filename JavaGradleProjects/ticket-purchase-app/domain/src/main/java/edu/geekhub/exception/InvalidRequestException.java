package edu.geekhub.exception;

public class InvalidRequestException extends TicketPurchaseAppException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
