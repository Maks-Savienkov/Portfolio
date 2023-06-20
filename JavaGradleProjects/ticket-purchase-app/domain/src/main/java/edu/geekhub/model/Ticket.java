package edu.geekhub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Ticket {
    private UUID id;
    private String seat;
    private TravelClass travelClass;
    private UUID userId;
    private UUID flightId;
}
