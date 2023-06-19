package edu.geekhub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Location {
    private UUID id;
    private String city;
    private String country;
}
