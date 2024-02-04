CREATE TABLE members
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    first_name     VARCHAR(255),
    last_name      VARCHAR(255),
    gender         VARCHAR(32),
    status         varchar(32),
    address        VARCHAR(255),
    phone_number   VARCHAR(55),
    birth_date     DATE,
    email          VARCHAR(255),
    username       VARCHAR(255),
    password       VARCHAR(255),

    CONSTRAINT pk_members PRIMARY KEY (id)
)