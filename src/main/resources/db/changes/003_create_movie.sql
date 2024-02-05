create table movie (
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name           varchar(255),
    description    varchar(255),
    duration       varchar(25),

    CONSTRAINT pk_movie PRIMARY KEY (id)
)