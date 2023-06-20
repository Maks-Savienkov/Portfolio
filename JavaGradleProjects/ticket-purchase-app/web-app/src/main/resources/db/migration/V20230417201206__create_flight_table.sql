CREATE TABLE flight (
    id UUID NOT NULL,
    status VARCHAR(10) NOT NULL,
    date_time TIMESTAMP NOT NULL,
    economy_seats VARCHAR,
    business_seats VARCHAR,
    first_class_seats VARCHAR,
    airport_from_id UUID NOT NULL,
    airport_to_id UUID NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_airport_from
        FOREIGN KEY(airport_from_id)
            REFERENCES airport(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_airport_to
        FOREIGN KEY(airport_to_id)
            REFERENCES airport(id)
            ON DELETE CASCADE
);
