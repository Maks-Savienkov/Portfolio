package edu.geekhub.repository.impl;

import edu.geekhub.model.Ticket;
import edu.geekhub.model.TravelClass;
import edu.geekhub.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_TICKET = """
        INSERT INTO ticket (
            id,
            seat,
            travel_class,
            user_id,
            flight_id
        )
        VALUES (
            :id,
            :seat,
            :travel_class,
            :user_id,
            :flight_id
        );
        """;

    private static final String DELETE_TICKET = """
        DELETE FROM ticket
            WHERE id=:id;
        """;

    private static final String FETCH_TICKET_BY_ID = """
        SELECT * FROM ticket
            WHERE id=:id;
        """;

    private static final String FETCH_ALL_TICKETS = """
        SELECT * FROM ticket;
        """;

    private static final String FETCH_ALL_TICKETS_BY_USER_ID = """
        SELECT * FROM ticket
            WHERE user_id=:user_id;
        """;

    private static final String FETCH_ALL_TICKETS_BY_FLIGHT_ID = """
        SELECT * FROM ticket
            WHERE flight_id=:flight_id;
        """;

    @Override
    public void insert(Ticket ticket) {
        jdbcTemplate.update(
            INSERT_TICKET,
            new MapSqlParameterSource()
                .addValue("id", ticket.getId())
                .addValue("seat", ticket.getSeat())
                .addValue("travel_class", ticket.getTravelClass().toString())
                .addValue("user_id", ticket.getUserId())
                .addValue("flight_id", ticket.getFlightId())
        );
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(
            DELETE_TICKET,
            new MapSqlParameterSource()
                .addValue("id", id)
        );
    }

    @Override
    public Optional<Ticket> findById(UUID id) {
        Ticket ticket = jdbcTemplate.queryForObject(FETCH_TICKET_BY_ID,
            new MapSqlParameterSource()
                .addValue("id", id),
            getTicketRowMapper()
        );
        if (Objects.isNull(ticket)) {
            return Optional.empty();
        } else {
            return Optional.of(ticket);
        }
    }

    @Override
    public List<Ticket> getAll() {
        return jdbcTemplate.query(
            FETCH_ALL_TICKETS,
            getTicketRowMapper()
        );
    }

    @Override
    public List<Ticket> getAllByUserId(UUID userId) {
        return jdbcTemplate.query(
            FETCH_ALL_TICKETS_BY_USER_ID,
            new MapSqlParameterSource()
                .addValue("user_id", userId),
            getTicketRowMapper()
        );
    }

    @Override
    public List<Ticket> getAllByFlightId(UUID flightId) {
        return jdbcTemplate.query(
            FETCH_ALL_TICKETS_BY_FLIGHT_ID,
            new MapSqlParameterSource()
                .addValue("flight_id", flightId),
            getTicketRowMapper()
        );
    }

    private static RowMapper<Ticket> getTicketRowMapper() {
        return (rs, rowNum) -> Ticket.builder()
            .id(UUID.fromString(rs.getString("id")))
            .seat(rs.getString("seat"))
            .travelClass(TravelClass.valueOf(rs.getString("travel_class")))
            .userId(UUID.fromString(rs.getString("user_id")))
            .flightId(UUID.fromString(rs.getString("flight_id")))
            .build();
    }
}
