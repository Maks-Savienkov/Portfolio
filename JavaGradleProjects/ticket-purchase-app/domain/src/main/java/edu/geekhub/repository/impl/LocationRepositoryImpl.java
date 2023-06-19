package edu.geekhub.repository.impl;

import edu.geekhub.model.Location;
import edu.geekhub.repository.LocationRepository;
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
public class LocationRepositoryImpl implements LocationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_LOCATION = """
        INSERT INTO location (id, city, country) VALUES (:id, :city, :country);
        """;

    private static final String DELETE_LOCATION = """
        DELETE FROM location WHERE id=:id;
        """;

    private static final String FETCH_LOCATION_BY_ID = """
        SELECT * FROM location WHERE id=:id;
        """;

    private static final String FETCH_ALL_LOCATIONS = """
        SELECT * FROM location;
        """;

    @Override
    public void insert(Location location) {
        jdbcTemplate.update(
            INSERT_LOCATION,
            new MapSqlParameterSource()
                .addValue("id", location.getId())
                .addValue("city", location.getCity())
                .addValue("country", location.getCountry())
        );
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(
            DELETE_LOCATION,
            new MapSqlParameterSource()
                .addValue("id", id)
        );
    }

    @Override
    public Optional<Location> findById(UUID id) {
        Location location = jdbcTemplate.queryForObject(
            FETCH_LOCATION_BY_ID,
            new MapSqlParameterSource()
                .addValue("id", id),
            getLocationRowMapper()
        );
        if (Objects.isNull(location)) {
            return Optional.empty();
        } else {
            return Optional.of(location);
        }
    }

    @Override
    public List<Location> getAll() {
        return jdbcTemplate.query(
            FETCH_ALL_LOCATIONS,
            getLocationRowMapper()
        );
    }

    private static RowMapper<Location> getLocationRowMapper() {
        return (rs, rowNum) -> Location.builder()
            .id(UUID.fromString(rs.getString("id")))
            .city(rs.getString("city"))
            .country(rs.getString("country"))
            .build();
    }
}
