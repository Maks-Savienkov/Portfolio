CREATE TABLE ticket (
    id UUID NOT NULL,
    seat VARCHAR(10) NOT NULL,
    travel_class VARCHAR(10) NOT NULL,
    user_id UUID NOT NULL,
    flight_id UUID NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
            REFERENCES "user"(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_flight
        FOREIGN KEY(flight_id)
            REFERENCES flight(id)
            ON DELETE CASCADE
);
