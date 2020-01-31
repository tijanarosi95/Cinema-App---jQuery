DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Movies;

CREATE TABLE Users(
    userName varchar(30) not null,
    password varchar(30) not null,
    dateRegistration text varchar(20) not null,
    role varchar(10) not null default 'USER',
    active bool not null,
    PRIMARY KEY(userName)
);

INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('markom123','mmmm9', "2020-01-02 20:57",'ADMIN', true);
INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('jovanj123', 'jjjj9', "2020-01-03 20:02", 'ADMIN', true);
INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('nikolan', 'nikolic123', "2020-01-05 23:57", 'USER', true);
INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('tijanar', 'tija1234', "2020-01-01 09:57", 'USER', true);
INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('aleksa1','alek34', "2020-01-02 17:57",'ADMIN', true);
INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('petarp', 'pera9', "2020-01-03 15:02", 'ADMIN', true);
INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('boro234', 'sifra345', "2020-01-05 18:57", 'USER', true);
INSERT INTO Users(userName, password, dateRegistration, role, active) VALUES ('milos23', '1234', "2020-01-01 12:57", 'USER', true);


CREATE TABLE Movies(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name varchar(30) not null,
    director varchar(20) not null,
    actors varchar(50) not null,
    genre varchar(50) not null,
    duration int not null,
    distributer varchar(20) not null,
    origin varchar(15) not null,
    year int not null,
    description varchar(50) not null,
    active bool not null
);

INSERT INTO Movies(name, director, actors, genre, duration, distributer, origin, year, description, active) 
VALUES ('Star Wars - The Rise of Skywalker', 'J.J.Abrams', 'Carrie Fisher, Mark Hamill, Adam Driver', 'ACTION, ADVENTURE, FANTASY', 142, 'Walt Disney Studios', 'United States', 2019,
'The Rise of Skywalker follows Rey, Finn, and Poe Dameron', true);
INSERT INTO Movies(name, director, actors, genre, duration, distributer, origin, year, description, active)
VALUES ('Little Women', 'Greta Gerwig', 'Saoirse Ronan, Emma Watson, Florence Pugh', 'DRAMA', 135, 'Sony Picture Realeasing', 'United States', 2019, 
'Jo March reflects back and forth on her life, telling the beloved story of the March sisters - four young women each determined to live life on their own terms.', true);
INSERT INTO Movies(name, director, actors, genre, duration, distributer, origin, year, description, active)
VALUES ('Joker', 'Todd Philips', 'Joaquin Pheoenix, Robert De Niro','CRIME, DRAMA, THRILLER', 122, 'Warner Bros, Pictures', 'United States', 2019,
'In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society. Path brings him face-to-face with his alter-ego: the Joker.', true);
INSERT INTO Movies(name, director, actors, genre, duration, distributer, origin, year, description, active)
VALUES ('The Godfather', 'Francis Ford Coppola', 'Marlon Brando, Al Pacino', 'CRIME, DRAMA', 175, 'Paramount Pictures', 'United States', 1972, 
'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', true);
INSERT INTO Movies(name, director, actors, genre, duration, distributer, origin, year, description, active)
VALUES ('The Dark Knight', 'Christopher Nolan', 'Christian Bale, Heath Ledger', 'ACTION, CRIME, DRAMA', 152, 'Warner Bros, Pictures', 'United States, United Kingdom', 2008,
'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', true);
INSERT INTO Movies(name, director, actors, genre, duration, distributer, origin, year, description, active)
VALUES ('Schindlers List', 'Steven Spielberg', 'Liam Neeson, Ralph Fiennes', 'DRAMA, HISTORY', 195, 'Universal Pictures', 'United States', 1993, 
'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', true);

CREATE TABLE ProjectionType(
    id int PRIMARY KEY,
    name varchar(5) NOT NULL
);

Insert into ProjectionType(id, name) values(1,'2D');
Insert into ProjectionType(id, name) values(2,'3D');
Insert into ProjectionType(id, name) values(3,'4D');



CREATE TABLE Hall(
    id INTEGER PRIMARY KEY,
    name varchar(5) not null,
    projectionType varchar(10) not null
);

Insert into Hall(id, name, projectionType) values (1, 'A1', '1,2');
Insert into Hall(id, name, projectionTYpe) values (2, 'A2', '2,3');
Insert into Hall(id, name, projectionTYpe) values (3, 'A3', '3');


CREATE TABLE Projections(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    movieid INTEGER not null,
    type INTEGER not null,
    hall INTEGER not null,
    datetime text varchar(20) not null,
    price double not null,
    user varchar(30) not null,
    active bool not null,
    FOREIGN KEY(type) REFERENCES ProjectionType(id),
    FOREIGN KEY(movieid) REFERENCES Movies(id),
    FOREIGN KEY(hall) REFERENCES Hall(id),
    FOREIGN KEY(user) REFERENCES Users(userName)  
);

Insert into Projections(movieid, type, hall, datetime, price, user, active) values (1, 2, 1, "2020-01-30 15:00", 350, 'markom123', true);
Insert into Projections(movieid, type, hall, datetime, price, user, active) values (2, 1, 1, "2020-01-31 18:00", 400, 'markom123', true);

SELECT * FROM Users;
SELECT * FROM Movies;
select * from ProjectionType;
select * from Hall;
select * from Projections;
