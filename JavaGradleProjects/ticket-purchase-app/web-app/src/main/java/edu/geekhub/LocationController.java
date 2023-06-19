package edu.geekhub;

import edu.geekhub.model.Location;
import edu.geekhub.service.LocationService;
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
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public Location create(
        @RequestParam("city") String city,
        @RequestParam("country") String country
    ) {
        return locationService.create(city, country);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable("id") UUID id
    ) {
        locationService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping
    public List<Location> getAll() {
        return locationService.getAll();
    }

    @GetMapping("/{id}")
    public Location getById(
        @PathVariable("id") UUID id
    ) {
        return locationService.getById(id);
    }
}
