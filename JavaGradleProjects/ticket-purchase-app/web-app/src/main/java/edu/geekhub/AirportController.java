package edu.geekhub;

import edu.geekhub.model.Airport;
import edu.geekhub.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public Airport create(
        @RequestParam("name") String name,
        @RequestParam("location_id") UUID locationId
    ) {
        return airportService.create(name, locationId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable("id") UUID id
    ) {
        airportService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping
    public List<Airport> getAll() {
        return airportService.getAll();
    }

    @GetMapping("/{id}")
    public Airport getById(
        @PathVariable("id") UUID id
    ) {
        return airportService.getById(id);
    }

    @GetMapping("/locations/{id}")
    public List<Airport> getAllByLocationId(
        @PathVariable("id") UUID locationId
    ) {
        return airportService.getAllByLocationId(locationId);
    }
}
