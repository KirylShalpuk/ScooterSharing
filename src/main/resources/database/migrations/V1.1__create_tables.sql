BEGIN;

CREATE TABLE users(
    id uuid not null primary key,
    version int4,
    firstname varchar(255),
    lastname varchar(255),
    phone_number varchar(255) not null unique,
    password varchar(255),
    email varchar(255) not null unique,
    photo_url varchar(500),
    active boolean default false,
    role_id uuid
);

CREATE TABLE roles(
    id uuid not null primary key,
    version int4,
    name varchar(255) not null unique,
    description varchar(255),
    active boolean default false,
    admin boolean default false,
    modifiable boolean default false
);

CREATE TABLE scooters(
    id uuid not null primary key,
    version int4,
    manufacturer varchar(255) not null,
    model varchar(255) not null,
    photo_url varchar(500),
    battery_charge int2,
    last_service timestamp,
    software_version varchar(255),
    active boolean default false,
    charging boolean default false,
    location_id uuid,
    status varchar(255)
);

CREATE TABLE tariffs(
    id uuid not null primary key,
    version int4,
    name varchar(255) not null unique,
    description varchar(255),
    costs int4,
    active boolean default false
);

CREATE TABLE rides(
    id uuid not null primary key,
    version int4,
    start_time timestamp,
    end_time timestamp,
    ride_status varchar(255),
    tariff_id uuid not null,
    user_id uuid not null,
    scooter_id uuid not null,
    payment_status varchar(255)
);

CREATE TABLE payments(
    id uuid not null primary key,
    version int4,
    country varchar(255),
    address varchar(255),
    post_code varchar(255),
    phone_number varchar(255) not null,
    user_id uuid not null
);

CREATE TABLE cards(
    id uuid not null primary key,
    version int4,
    card_number varchar(255),
    card_holder varchar(255),
    date_expiration varchar(255),
    email varchar(255),
    main boolean default false,
    payment_id uuid
);

CREATE TABLE tokens(
    id uuid not null primary key,
    version int4,
    user_id uuid not null,
    creation_date timestamp,
    token varchar(1000) unique not null,
    active boolean default false
);

CREATE TABLE locations(
    id uuid not null primary key,
    version int4,
    country varchar(255),
    city varchar(255),
    street varchar(255),
    building varchar(255),
    coordinates_id uuid
);

CREATE TABLE coordinates(
    id uuid not null primary key,
    version int4,
    latitude varchar(255),
    longitude varchar(255)
);

CREATE TABLE user_locations(
    id uuid not null primary key,
    version int4,
    user_id uuid not null,
    location_id uuid not null,
    position_time timestamp
);

CREATE TABLE ride_locations(
    id uuid not null primary key,
    version int4,
    ride_id uuid not null,
    location_id uuid not null,
    position_time timestamp
);

CREATE TABLE scooter_statistic(
    id uuid not null primary key,
    version int4,
    count int2,
    date date,
    time time,
    location_id uuid NOT NULL
);

ALTER TABLE users ADD CONSTRAINT fk_users_role_id FOREIGN KEY (role_id) REFERENCES roles;
ALTER TABLE payments ADD CONSTRAINT fk_payments_user_id FOREIGN KEY (user_id) REFERENCES users ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE rides ADD CONSTRAINT fk_rides_tariff_id FOREIGN KEY (tariff_id) REFERENCES tariffs;
ALTER TABLE rides ADD CONSTRAINT fk_rides_user_id FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE rides ADD CONSTRAINT fk_rides_scooter_id FOREIGN KEY (scooter_id) REFERENCES scooters;
ALTER TABLE cards ADD CONSTRAINT fk_cards_payment_id FOREIGN KEY (payment_id) REFERENCES payments ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE scooters ADD CONSTRAINT uk_scooters_battery_charge CHECK (battery_charge <= 100);
ALTER TABLE scooters ADD CONSTRAINT fk_scooters_location_id FOREIGN KEY (location_id) REFERENCES locations;
ALTER TABLE tokens ADD CONSTRAINT  fk_tokens_user_id FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE locations ADD CONSTRAINT fk_locations_coordinates_id FOREIGN KEY (coordinates_id) REFERENCES coordinates ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE user_locations ADD CONSTRAINT fk_user_locations_user_id FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE user_locations ADD CONSTRAINT fk_user_locations_location_id FOREIGN KEY (location_id) REFERENCES locations;
ALTER TABLE ride_locations ADD CONSTRAINT fk_ride_locations_ride_id FOREIGN KEY (ride_id) REFERENCES rides;
ALTER TABLE ride_locations ADD CONSTRAINT fk_ride_locations_location_id FOREIGN KEY (location_id) REFERENCES locations;
ALTER TABLE scooter_statistic ADD CONSTRAINT fk_scooter_statistic_location_id FOREIGN KEY (location_id) REFERENCES locations;

COMMIT;