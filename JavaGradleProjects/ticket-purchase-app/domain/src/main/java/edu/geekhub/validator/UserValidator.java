package edu.geekhub.validator;

import edu.geekhub.exception.validation.UserValidationException;
import edu.geekhub.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Component
public class UserValidator {
    private final UserValidationException exception;

    public UserValidator() {
        this.exception = new UserValidationException();
    }

    public void validate(User user) {
        if (Objects.isNull(user)) {
            exception.addMessage("User cannot be null.");
        } else {
            validateId(user.getId());
            validateName(user.getName());
            validateSurname(user.getSurname());
            validateBirthdate(user.getBirthdate());
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

    private void validateName(String name) {
        if (name == null) {
            exception.addMessage("Name cannot be null. ");
        } else {
            if (name.isEmpty()) {
                exception.addMessage("Name cannot be empty. ");
            }
            if (!name.matches("^[A-Za-zА-ЩЬЮЯҐЄІЇа-щьюяґєії]+$")) {
                exception.addMessage("Name must contain only letters. ");
            }
        }
    }

    private void validateSurname(String surname) {
        if (surname == null) {
            exception.addMessage("Surname cannot be null. ");
        } else {
            if (surname.isEmpty()) {
                exception.addMessage("Surname cannot be empty. ");
            }
            if (!surname.matches("^[A-Za-zА-ЩЬЮЯҐЄІЇа-щьюяґєії]+$")) {
                exception.addMessage("Surname must contain only letters. ");
            }
        }
    }

    private void validateBirthdate(LocalDate birthdate) {
        if (birthdate == null) {
            exception.addMessage("Birthdate cannot be null. ");
        } else {
            if (birthdate.getYear() < 1920) {
                exception.addMessage("Birthdate cannot be before 01.01.1920. ");
            } else if (birthdate.isAfter(LocalDate.now().minusYears(18))) {
                exception.addMessage(
                    "The user must be of legal age. "
                );
            }
        }
    }
}
