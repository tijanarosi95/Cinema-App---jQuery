DROP TABLE IF EXISTS Users;

CREATE TABLE Users(
    userName varchar(30) not null,
    password varchar(30) not null,
    dateRegistration text varchar(10) not null,
    role varchar(10) not null default 'USER',
    PRIMARY KEY(userName)
);

INSERT INTO Users(userName, password, dateRegistration, role) VALUES ('markom123','mmmm9',CURRENT_DATE,'ADMIN');
INSERT INTO Users(userName, password, dateRegistration, role) VALUES ('jovanj123', 'jjjj9', CURRENT_DATE, 'ADMIN');
INSERT INTO Users(userName, password, dateRegistration, role) VALUES ('nikolan', 'nikolic123', CURRENT_DATE, 'USER');
INSERT INTO Users(userName, password, dateRegistration, role) VALUES ('tijanar', 'tija1234', CURRENT_DATE, 'USER');

SELECT * FROM Users;