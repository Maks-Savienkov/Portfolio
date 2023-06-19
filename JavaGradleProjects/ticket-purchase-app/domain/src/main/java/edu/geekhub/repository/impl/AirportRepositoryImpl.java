package edu.geekhub.repository.impl;

import edu.geekhub.model.Airport;
import edu.geekhub.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AirportRepositoryImpl implements AirportRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_AIRPORT = """
        INSERT INTO airport (
            id,
            name,
            location_id
        )
        VALUES (
            :id,
            :name,
            :location_id
        );
        """;

    private static final String DELETE_AIRPORT = """
        DELETE FROM airport
            WHERE id=:id;
        """;

    private static final String FETCH_AIRPORT_BY_ID = """
        SELECT * FROM airport
            WHERE id=:id;
        """;

    private static final String FETCH_ALL_AIRPORTS = """
        SELECT * FROM airport;
        """;

    private static final String FETCH_ALL_AIRPORTS_BY_LOCATION_ID = """
        SELECT * FROM airport
            WHERE location_id=:location_id;
        """;

    @Override
    public void insert(Airport airport) {
        jdbcTemplate.update(
            INSERT_AIRPORT,
            new MapSqlParameterSource()
                .addValue("id", airport.getId())
                .addValue("name", airport.getName())
                .addValue("location_id", airport.getLocationId())
        );
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(
            DELETE_AIRPORT,
            new MapSqlParameterSource()
                .addValue("id", id)
        );
    }

    @Override
    public Optional<Airport> findById(UUID id) {
        List<Airport> airports = jdbcTemplate.query(
            FETCH_AIRPORT_BY_ID,
            new MapSqlParameterSource()
                .addValue("id", id),
            getAirportRowMapper()
        );
        if (airports.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(airports.get(0));
        }
    }

    @Override
    public List<Airport> getAll() {
        return jdbcTemplate.query(
            FETCH_ALL_AIRPORTS,
            getAirportRowMapper()
        );
    }

    @Override
    public List<Airport> getAllByLocationId(UUID locationId) {
        return jdbcTemplate.query(
            FETCH_ALL_AIRPORTS_BY_LOCATION_ID,
            new MapSqlParameterSource()
                .addValue("location_id", locationId),
            getAirportRowMapper()
        );
    }

    private static RowMapper<Airport> getAirportRowMapper() {
        return (rs, rowNum) -> Airport.builder()
            .id(UUID.fromString(rs.getString("id")))
            .name(rs.getString("name"))
            .locationId(UUID.fromString(rs.getString("location_id")))
            .build();
    }
}
