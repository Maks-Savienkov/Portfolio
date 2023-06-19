package edu.geekhub;

import edu.geekhub.model.User;
import edu.geekhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(
        @RequestParam("name") String name,
        @RequestParam("surname") String surname,
        @RequestParam("birthdate") LocalDate birthdate
    ) {
        return userService.create(name, surname, birthdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable("id") UUID id
    ) {
        userService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(
        @PathVariable("id") UUID userId
    ) {
        return userService.getById(userId);
    }
}
