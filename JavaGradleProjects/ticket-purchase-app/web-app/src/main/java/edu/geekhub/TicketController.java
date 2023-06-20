package edu.geekhub;

import edu.geekhub.model.Ticket;
import edu.geekhub.model.TravelClass;
import edu.geekhub.service.TicketService;
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
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public Ticket create(
        @RequestParam("seat") String seat,
        @RequestParam("travel_class") TravelClass travelClass,
        @RequestParam("user_id") UUID userId,
        @RequestParam("flight_id") UUID flightId
    ) {
        return ticketService.create(seat, travelClass, userId, flightId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable("id") UUID id
    ) {
        ticketService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping
    public List<Ticket> getAll() {
        return ticketService.getAll();
    }

    @GetMapping("/{id}")
    public Ticket getById(
        @PathVariable("id") UUID id
    ) {
        return ticketService.getById(id);
    }

    @GetMapping("/user/{id}")
    public List<Ticket> getAllByUserId(
        @PathVariable("id") UUID userId
    ) {
        return ticketService.getAllByUserId(userId);
    }

    @GetMapping("/flight/{id}")
    public List<Ticket> getAllByFlightId(
        @PathVariable("id") UUID flightId
    ) {
        return ticketService.getAllByFlightId(flightId);
    }
}
