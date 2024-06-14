CREATE TABLE IF NOT EXISTS SPRINKLER
(
        id bigint not null,
        device_id varchar(255) not null unique,
        location_alias varchar(255),
        location_coordinates varchar(255),
        primary key (id)
);

CREATE TABLE IF NOT EXISTS SPRINKLER_RULE (
        duration_minutes integer not null,
        created_at timestamp(6),
        id bigint not null,
        last_updated_at timestamp(6),
        sprinkler_id bigint,
        sprinkler_device_id varchar(255) not null,
        start_time varchar(255) not null,
        user_id varchar(255) not null,
        primary key (id));

alter table if exists SPRINKLER_RULE
    add constraint FK6sno4c5ftu5nvhc47wg8be26e
    foreign key (sprinkler_id)
    references sprinkler;
