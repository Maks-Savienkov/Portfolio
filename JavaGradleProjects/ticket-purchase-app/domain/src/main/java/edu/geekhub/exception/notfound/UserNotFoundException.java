package edu.geekhub.exception.notfound;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(UUID id) {
        super("User with id '%s' not found".formatted(id.toString()));
    }
}
