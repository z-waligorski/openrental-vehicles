CREATE TABLE cars
(
    id                 BIGSERIAL NOT NULL,
    brand              VARCHAR(255),
    model              VARCHAR(255),
    year_of_production INTEGER,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    updated_at         TIMESTAMP WITHOUT TIME ZONE,
    seats              INTEGER,
    fuel_consumption   FLOAT,
    CONSTRAINT pk_cars PRIMARY KEY (id)
);

CREATE TABLE motorcycles
(
    id                 BIGSERIAL NOT NULL,
    brand              VARCHAR(255),
    model              VARCHAR(255),
    year_of_production INTEGER,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    updated_at         TIMESTAMP WITHOUT TIME ZONE,
    engine_capacity    FLOAT,
    CONSTRAINT pk_motorcycles PRIMARY KEY (id)
);