package edu.geekhub.repository;

import edu.geekhub.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void insert(User user);

    void delete(UUID id);

    Optional<User> findById(UUID id);

    List<User> getAll();
}
