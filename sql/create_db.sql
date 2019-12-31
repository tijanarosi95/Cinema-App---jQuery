DROP TABLE IF EXISTS Users;

CREATE TABLE Users(
    userName varchar(30) not null,
    password varchar(30) not null,
    dateRegistration text varchar(10) not null,
    role varchar(10) not null default 'USER',
    active bool not null,
    PRIMARY KEY(userName)
);

INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('markom123','mmmm9',CURRENT_TIMESTAMP,'ADMIN', true);
INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('jovanj123', 'jjjj9', CURRENT_TIMESTAMP, 'ADMIN', true);
INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('nikolan', 'nikolic123', CURRENT_TIMESTAMP, 'USER', true);
INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('tijanar', 'tija1234', CURRENT_TIMESTAMP, 'USER', true);

SELECT * FROM Users;