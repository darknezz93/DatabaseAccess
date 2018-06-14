CREATE TABLE COUNTRY(id int primary key, name varchar(255));

CREATE TABLE CITY(id int primary key, name varchar(255), country_id int, FOREIGN KEY (country_id) REFERENCES Country(id), avg_population int);

CREATE SEQUENCE IF NOT EXISTS countrySeq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS citySeq START WITH 1 INCREMENT BY 1;

INSERT INTO COUNTRY (id, name) VALUES (countrySeq.nextval, 'PL');
INSERT INTO COUNTRY (id, name) VALUES (countrySeq.nextval, 'UK');
INSERT INTO COUNTRY (id, name) VALUES (countrySeq.nextval, 'FR');

INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'Warsaw', 1, 1600000);
INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'Poznan', 1, 600000);
INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'Krakow', 1, 750000);
INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'London', 2, 8000000);
INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'Liverpool', 2, 550000);
INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'Paris', 3, 2000000);

COMMIT;