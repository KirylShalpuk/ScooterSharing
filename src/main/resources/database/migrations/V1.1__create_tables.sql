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
    ride_status varchar (255),
    tariff_id uuid not null,
    user_id uuid not null,
    scooter_id uuid not null
);

ALTER TABLE users ADD CONSTRAINT fk_users_role_id FOREIGN KEY (role_id) REFERENCES roles;
ALTER TABLE rides ADD CONSTRAINT fk_rides_tariff_id FOREIGN KEY (tariff_id) REFERENCES tariffs;
ALTER TABLE rides ADD CONSTRAINT fk_rides_user_id FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE rides ADD CONSTRAINT fk_rides_scooter_id FOREIGN KEY (scooter_id) REFERENCES scooters;
ALTER TABLE scooters ADD CONSTRAINT uk_scooters_battery_charge CHECK (battery_charge <= 100);

COMMIT;