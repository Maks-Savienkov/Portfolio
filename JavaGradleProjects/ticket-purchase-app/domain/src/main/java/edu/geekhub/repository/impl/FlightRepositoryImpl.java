package edu.geekhub.repository.impl;

import edu.geekhub.model.FlightStatus;
import edu.geekhub.model.dto.FlightPersistenceDto;
import edu.geekhub.repository.FlightRepository;
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
public class FlightRepositoryImpl implements FlightRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_FLIGHT = """
        INSERT INTO flight (
            id,
            status,
            date_time,
            economy_seats,
            business_seats,
            first_class_seats,
            airport_from_id,
            airport_to_id
        )
        VALUES (
            :id,
            :status,
            :date_time,
            :economy_seats,
            :business_seats,
            :first_class_seats,
            :airport_from_id,
            :airport_to_id
        );
        """;

    private static final String DELETE_FLIGHT = """
        DELETE FROM flight
            WHERE id=:id;
        """;

    private static final String UPDATE_FLIGHT = """
        UPDATE flight
            SET
                status=:status,
                date_time=:date_time,
                economy_seats=:economy_seats,
                business_seats=:business_seats,
                first_class_seats=:first_class_seats,
                airport_from_id=:airport_from_id,
                airport_to_id=:airport_to_id
            WHERE id=:id;
        """;

    private static final String FETCH_FLIGHT_BY_ID = """
        SELECT * FROM flight
            WHERE id=:id;
        """;

    private static final String FETCH_ALL_FLIGHTS = """
        SELECT * FROM flight;
        """;

    private static final String FETCH_ALL_FLIGHTS_BY_AIRPORT_ID = """
        SELECT * FROM flight
            WHERE airport_from_id=:airport_id OR airport_to_id=:airport_id;
        """;

    private static final String FETCH_ALL_FLIGHTS_BY_AIRPORT_FROM_ID = """
        SELECT * FROM flight
            WHERE airport_from_id=:airport_id;
        """;

    private static final String FETCH_ALL_FLIGHTS_BY_AIRPORT_TO_ID = """
        SELECT * FROM flight
            WHERE airport_to_id=:airport_id;
        """;

    @Override
    public void insert(FlightPersistenceDto flight) {
        jdbcTemplate.update(
            INSERT_FLIGHT,
            new MapSqlParameterSource()
                .addValue("id", flight.getId())
                .addValue("status", flight.getStatus().toString())
                .addValue("date_time", flight.getDateTime())
                .addValue("economy_seats", flight.getEconomySeats())
                .addValue("business_seats", flight.getBusinessSeats())
                .addValue("first_class_seats", flight.getFirstClassSeats())
                .addValue("airport_from_id", flight.getAirportFromId())
                .addValue("airport_to_id", flight.getAirportToId())
        );
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(
            DELETE_FLIGHT,
            new MapSqlParameterSource()
                .addValue("id", id)
        );
    }

    @Override
    public void update(FlightPersistenceDto flight) {
        jdbcTemplate.update(
            UPDATE_FLIGHT,
            new MapSqlParameterSource()
                .addValue("id", flight.getId())
                .addValue("status", flight.getStatus().toString())
                .addValue("date_time", flight.getDateTime())
                .addValue("economy_seats", flight.getEconomySeats())
                .addValue("business_seats", flight.getBusinessSeats())
                .addValue("first_class_seats", flight.getFirstClassSeats())
                .addValue("airport_from_id", flight.getAirportFromId())
                .addValue("airport_to_id", flight.getAirportToId())
        );
    }

    @Override
    public Optional<FlightPersistenceDto> findById(UUID id) {
        FlightPersistenceDto flight = jdbcTemplate.queryForObject(
            FETCH_FLIGHT_BY_ID,
            new MapSqlParameterSource()
                .addValue("id", id),
            getFlightRowMapper()
        );
        if (Objects.isNull(flight)) {
            return Optional.empty();
        } else {
            return Optional.of(flight);
        }
    }

    @Override
    public List<FlightPersistenceDto> getAll() {
        return jdbcTemplate.query(
            FETCH_ALL_FLIGHTS,
            getFlightRowMapper()
        );
    }

    @Override
    public List<FlightPersistenceDto> getAllByAirportId(UUID airportId) {
        return jdbcTemplate.query(
            FETCH_ALL_FLIGHTS_BY_AIRPORT_ID,
            new MapSqlParameterSource()
                .addValue("airport_id", airportId),
            getFlightRowMapper()
        );
    }

    @Override
    public List<FlightPersistenceDto> getAllByAirportFromId(UUID airportFromId) {
        return jdbcTemplate.query(
            FETCH_ALL_FLIGHTS_BY_AIRPORT_FROM_ID,
            new MapSqlParameterSource()
                .addValue("airport_id", airportFromId),
            getFlightRowMapper()
        );
    }

    @Override
    public List<FlightPersistenceDto> getAllByAirportToId(UUID airportToId) {
        return jdbcTemplate.query(
            FETCH_ALL_FLIGHTS_BY_AIRPORT_TO_ID,
            new MapSqlParameterSource()
                .addValue("airport_id", airportToId),
            getFlightRowMapper()
        );
    }

    private static RowMapper<FlightPersistenceDto> getFlightRowMapper() {
        return (rs, rowNum) -> FlightPersistenceDto.builder()
            .id(UUID.fromString(rs.getString("id")))
            .status(FlightStatus.valueOf(rs.getString("status")))
            .dateTime(rs.getTimestamp("date_time").toLocalDateTime())
            .economySeats(rs.getString("economy_seats"))
            .businessSeats(rs.getString("business_seats"))
            .firstClassSeats(rs.getString("first_class_seats"))
            .airportFromId(UUID.fromString(rs.getString("airport_from_id")))
            .airportToId(UUID.fromString(rs.getString("airport_to_id")))
            .build();
    }
}
