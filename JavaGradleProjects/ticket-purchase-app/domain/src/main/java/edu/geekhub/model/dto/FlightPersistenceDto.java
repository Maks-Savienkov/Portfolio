package edu.geekhub.model.dto;

import edu.geekhub.model.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class FlightPersistenceDto {
    private UUID id;
    private FlightStatus status;
    private LocalDateTime dateTime;
    private String economySeats;
    private String businessSeats;
    private String firstClassSeats;
    private UUID airportFromId;
    private UUID airportToId;
}
