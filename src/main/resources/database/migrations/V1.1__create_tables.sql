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
    charging boolean default false
);

ALTER TABLE users ADD CONSTRAINT fk_users_role_id FOREIGN KEY (role_id) REFERENCES roles;
ALTER TABLE scooters ADD CONSTRAINT uk_scooters_battery_charge CHECK (battery_charge <= 100);

COMMIT;