package edu.geekhub;

import edu.geekhub.model.Flight;
import edu.geekhub.model.FlightAirportDirection;
import edu.geekhub.model.FlightStatus;
import edu.geekhub.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public Flight create(@RequestBody Flight flight) {
        return flightService.create(flight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable("id") UUID id
    ) {
        flightService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @PostMapping("/{id}/status/{newStatus}")
    public Flight changeStatus(
        @PathVariable("id") UUID id,
        @PathVariable("newStatus") FlightStatus state
    ) {
        return flightService.changeStatus(id, state);
    }

    @GetMapping
    public List<Flight> getAll() {
        return flightService.getAll();
    }

    @GetMapping("/{id}")
    public Flight getById(
        @PathVariable("id") UUID id
    ) {
        return flightService.getById(id);
    }

    @GetMapping("/airport/{id}")
    public List<Flight> getAllByAirportId(
        @PathVariable("id") UUID airportId,
        @RequestParam("direction") FlightAirportDirection direction
    ) {
        return flightService.getAllByAirportId(airportId, direction);
    }
}
