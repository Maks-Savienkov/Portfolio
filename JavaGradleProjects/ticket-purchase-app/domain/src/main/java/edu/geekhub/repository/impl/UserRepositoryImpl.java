package edu.geekhub.repository.impl;

import edu.geekhub.model.User;
import edu.geekhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_USER = """
        INSERT INTO "user" (id, name, surname, birthdate) VALUES (:id, :name, :surname, :birthdate);
        """;

    private static final String DELETE_USER = """
        DELETE FROM "user" WHERE id=:id;
        """;

    private static final String FETCH_USER_BY_ID = """
        SELECT * FROM "user" WHERE id=:id;
        """;

    private static final String FETCH_ALL_USERS = """
        SELECT * FROM "user";
        """;

    @Override
    public void insert(User user) {
        jdbcTemplate.update(
            INSERT_USER,
            new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("surname", user.getSurname())
                .addValue(
                    "birthdate",
                    Timestamp.valueOf(LocalDateTime.of(
                        user.getBirthdate(), LocalTime.MIN
                    ))
                )
        );
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(
            DELETE_USER,
            new MapSqlParameterSource()
                .addValue("id", id)
        );
    }

    @Override
    public Optional<User> findById(UUID id) {
        User user = jdbcTemplate.queryForObject(FETCH_USER_BY_ID,
            new MapSqlParameterSource()
                .addValue("id", id),
            getUserRowMapper()
        );
        if (Objects.isNull(user)) {
            return Optional.empty();
        } else {
            return Optional.of(user);
        }
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(FETCH_ALL_USERS,
            getUserRowMapper()
        );
    }

    private static RowMapper<User> getUserRowMapper() {
        return (rs, rowNum) -> User.builder()
            .id(UUID.fromString(rs.getString("id")))
            .name(rs.getString("name"))
            .surname(rs.getString("surname"))
            .birthdate(
                rs.getTimestamp("birthdate")
                    .toLocalDateTime()
                    .toLocalDate()
            )
            .build();
    }
}
