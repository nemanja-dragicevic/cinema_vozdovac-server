create table actor (
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    first_name     varchar(255),
    last_name      varchar(255),
    gender         varchar(255),

    CONSTRAINT pk_actor PRIMARY KEY (id)
)