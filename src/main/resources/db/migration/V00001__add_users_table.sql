CREATE TABLE users (
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email VARCHAR(20) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    role varchar(50) NOT NULL
);