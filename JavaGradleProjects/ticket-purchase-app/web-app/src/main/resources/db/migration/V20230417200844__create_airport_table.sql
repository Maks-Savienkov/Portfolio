CREATE TABLE airport (
    id UUID NOT NULL,
    name VARCHAR(200) NOT NULL,
    location_id UUID NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_location
        FOREIGN KEY(location_id)
            REFERENCES location(id)
            ON DELETE CASCADE
);
