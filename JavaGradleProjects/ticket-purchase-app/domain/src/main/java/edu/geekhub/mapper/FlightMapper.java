package edu.geekhub.mapper;

import edu.geekhub.model.Flight;
import edu.geekhub.model.dto.FlightPersistenceDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightMapper {
    public FlightPersistenceDto toDto(Flight flight) {
        return FlightPersistenceDto.builder()
            .id(flight.getId())
            .status(flight.getStatus())
            .dateTime(flight.getDateTime())
            .economySeats(listToString(flight.getEconomySeats()))
            .businessSeats(listToString(flight.getBusinessSeats()))
            .firstClassSeats(listToString(flight.getFirstClassSeats()))
            .airportFromId(flight.getAirportFromId())
            .airportToId(flight.getAirportToId())
            .build();
    }

    public Flight toModel(FlightPersistenceDto dto) {
        return Flight.builder()
            .id(dto.getId())
            .status(dto.getStatus())
            .dateTime(dto.getDateTime())
            .economySeats(stringToList(dto.getEconomySeats()))
            .businessSeats(stringToList(dto.getBusinessSeats()))
            .firstClassSeats(stringToList(dto.getFirstClassSeats()))
            .airportFromId(dto.getAirportFromId())
            .airportToId(dto.getAirportToId())
            .build();
    }

    private List<String> stringToList(String string) {
        return Arrays.stream(string.split(" ")).toList();
    }

    private String listToString(List<String> list) {
        return list.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(" "));
    }
}
