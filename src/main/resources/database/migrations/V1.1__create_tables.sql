BEGIN;

CREATE TABLE users(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    phone_number VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    photo_url VARCHAR(500),
    active BOOLEAN DEFAULT FALSE,
    role_id VARCHAR(36)
);

CREATE TABLE roles(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255),
    active BOOLEAN DEFAULT FALSE,
    admin BOOLEAN DEFAULT FALSE,
    modifiable BOOLEAN DEFAULT FALSE
);

CREATE TABLE scooters(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    manufacturer VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    photo_url VARCHAR(500),
    battery_charge INT,
    last_service TIMESTAMP,
    software_version VARCHAR(255),
    active BOOLEAN DEFAULT FALSE,
    charging BOOLEAN DEFAULT FALSE,
    location_id VARCHAR(36),
    status VARCHAR(255)
);

CREATE TABLE tariffs(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255),
    costs INT,
    active BOOLEAN DEFAULT FALSE
);

CREATE TABLE rides(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    ride_status VARCHAR(255),
    tariff_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    scooter_id VARCHAR(36) NOT NULL,
    payment_status VARCHAR(255)
);

CREATE TABLE payments(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    country VARCHAR(255),
    address VARCHAR(255),
    post_code VARCHAR(255),
    phone_number VARCHAR(255) NOT NULL,
    user_id VARCHAR(36) NOT NULL
);

CREATE TABLE cards(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    card_number VARCHAR(255),
    card_holder VARCHAR(255),
    date_expiration VARCHAR(255),
    email VARCHAR(255),
    main BOOLEAN DEFAULT FALSE,
    payment_id VARCHAR(36)
);

CREATE TABLE tokens(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    user_id VARCHAR(36) NOT NULL,
    creation_date TIMESTAMP,
    token VARCHAR(255) UNIQUE NOT NULL,
    active BOOLEAN DEFAULT FALSE
);

CREATE TABLE locations(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    country VARCHAR(255),
    city VARCHAR(255),
    street VARCHAR(255),
    building VARCHAR(255),
    coordinates_id VARCHAR(36)
);

CREATE TABLE coordinates(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    latitude VARCHAR(255),
    longitude VARCHAR(255)
);

CREATE TABLE user_locations(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    user_id VARCHAR(36) NOT NULL,
    location_id VARCHAR(36) NOT NULL,
    position_time TIMESTAMP
);

CREATE TABLE ride_locations(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    version INT,
    ride_id VARCHAR(36) NOT NULL,
    location_id VARCHAR(36) NOT NULL,
    position_time TIMESTAMP
);

CREATE TABLE scooter_statistic(
    id uuid not null primary key,
    version int4,
    count int2,
    date date,
    time time,
    location_id uuid NOT NULL
);

-- ALTER TABLE users CONVERT TO CHARACTER SET utf8mb4;
-- ALTER TABLE cards CONVERT TO CHARACTER SET utf8mb4;
-- ALTER TABLE coordinates CONVERT TO CHARACTER SET utf8mb4;
-- ALTER TABLE locations CONVERT TO CHARACTER SET utf8mb4;
-- ALTER TABLE payments CONVERT TO CHARACTER SET utf8mb4;
-- ALTER TABLE ride_locations CONVERT TO CHARACTER SET utf8mb4;
-- ALTER TABLE rides CONVERT TO CHARACTER SET utf8mb4;
-- ALTER TABLE roles CONVERT TO CHARACTER SET utf8mb4;
-- ALTER TABLE scooters CONVERT TO CHARACTER SET utf8mb4;
-- ALTER TABLE tariffs CONVERT TO CHARACTER SET utf8mb4;
-- ALTER TABLE tokens CONVERT TO CHARACTER SET utf8mb4;
-- ALTER TABLE user_locations CONVERT TO CHARACTER SET utf8mb4;

ALTER TABLE users ADD CONSTRAINT fk_users_role_id FOREIGN KEY (role_id) REFERENCES roles(id);
ALTER TABLE payments ADD CONSTRAINT fk_payments_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE rides ADD CONSTRAINT fk_rides_tariff_id FOREIGN KEY (tariff_id) REFERENCES tariffs(id);
ALTER TABLE rides ADD CONSTRAINT fk_rides_user_id FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE rides ADD CONSTRAINT fk_rides_scooter_id FOREIGN KEY (scooter_id) REFERENCES scooters(id);
ALTER TABLE cards ADD CONSTRAINT fk_cards_payment_id FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE scooters ADD CONSTRAINT uk_scooters_battery_charge CHECK (battery_charge <= 100);
ALTER TABLE scooters ADD CONSTRAINT fk_scooters_location_id FOREIGN KEY (location_id) REFERENCES locations(id);
ALTER TABLE tokens ADD CONSTRAINT  fk_tokens_user_id FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE locations ADD CONSTRAINT fk_locations_coordinates_id FOREIGN KEY (coordinates_id) REFERENCES coordinates(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE user_locations ADD CONSTRAINT fk_user_locations_user_id FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_locations ADD CONSTRAINT fk_user_locations_location_id FOREIGN KEY (location_id) REFERENCES locations(id);
ALTER TABLE ride_locations ADD CONSTRAINT fk_ride_locations_ride_id FOREIGN KEY (ride_id) REFERENCES rides(id);
ALTER TABLE ride_locations ADD CONSTRAINT fk_ride_locations_location_id FOREIGN KEY (location_id) REFERENCES locations(id);
ALTER TABLE scooter_statistic ADD CONSTRAINT fk_scooter_statistic_location_id FOREIGN KEY (location_id) REFERENCES locations(id);



COMMIT;