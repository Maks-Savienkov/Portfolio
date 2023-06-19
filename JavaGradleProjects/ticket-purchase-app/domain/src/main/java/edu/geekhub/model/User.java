package edu.geekhub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String surname;
    private LocalDate birthdate;
}
