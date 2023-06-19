package edu.geekhub.service;

import edu.geekhub.exception.InvalidRequestException;
import edu.geekhub.exception.notfound.UserNotFoundException;
import edu.geekhub.exception.validation.UserValidationException;
import edu.geekhub.model.User;
import edu.geekhub.repository.UserRepository;
import edu.geekhub.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserValidator validator;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User create(
        String name,
        String surname,
        LocalDate birthdate
    ) {
        User user = new User(
            UUID.randomUUID(),
            name,
            surname,
            birthdate
        );

        try {
            validator.validate(user);
            logger.info("Try to add a new user.");
            repository.insert(user);
            logger.info("User added successfully.");
        } catch (UserValidationException e) {
            logger.error("Failed to create user. " + e.getMessage());
            throw new InvalidRequestException("Failed to create user. " + e.getMessage());
        }

        return user;
    }

    public void delete(UUID id) {
        logger.info("Deleting user.");
        repository.delete(id);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public User getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public boolean isExistsUserWithId(UUID userId) {
        return repository.findById(userId).isPresent();
    }
}
