package edu.geekhub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Flight {
    private UUID id;
    private FlightStatus status;
    private LocalDateTime dateTime;
    private List<String> economySeats;
    private List<String> businessSeats;
    private List<String> firstClassSeats;
    private UUID airportFromId;
    private UUID airportToId;
}
