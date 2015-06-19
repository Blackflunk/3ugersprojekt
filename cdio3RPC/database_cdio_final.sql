CREATE DATABASE IF NOT EXISTS 12_db;
use 12_db;
DROP TABLE IF EXISTS receptkomponent;
DROP TABLE IF EXISTS produktbatchkomponent;
DROP TABLE IF EXISTS produktbatch;
DROP TABLE IF EXISTS operatoer;
DROP TABLE IF EXISTS rettighed;
DROP TABLE IF EXISTS raavarebatch;
DROP TABLE IF EXISTS raavare;
DROP TABLE IF EXISTS recept;


CREATE TABLE rettighed(
-- admin 1, farma 2, vaerk 3, opr 4 og inaktiv 0
rettighedsniveau VARCHAR(1),
stilling text,
PRIMARY KEY (rettighedsniveau));

CREATE TABLE operatoer(
opr_id INTEGER(8),
opr_navn VARCHAR(20),
ini VARCHAR(4),
cpr VARCHAR(11),
password VARCHAR(20),
rettighedsniveau VARCHAR(1),
PRIMARY KEY (opr_id),
FOREIGN KEY (rettighedsniveau) REFERENCES rettighed(rettighedsniveau));

CREATE TABLE raavare(
raavare_id INTEGER(11),
raavare_navn text,
leverandoer VARCHAR(20), 
PRIMARY KEY (raavare_id));

CREATE TABLE recept(
recept_id INTEGER(11),
recept_navn text,
PRIMARY KEY (recept_id));

CREATE TABLE raavarebatch(
rb_id INTEGER(11),
raavare_id INTEGER(11),
maengde DOUBLE,
PRIMARY KEY (rb_id),
FOREIGN KEY (raavare_id) REFERENCES raavare(raavare_id)
);

CREATE TABLE produktbatch(
pb_id INTEGER(11),
recept_id INTEGER(11),
status INTEGER(11),
PRIMARY KEY (pb_id),
FOREIGN KEY (recept_id) REFERENCES recept(recept_id));

CREATE TABLE receptkomponent(
recept_id INTEGER(11),
raavare_id INTEGER(11),
nom_netto DOUBLE,
tolerance DOUBLE,
PRIMARY KEY (raavare_id, recept_id),
FOREIGN KEY (raavare_id) REFERENCES raavare(raavare_id),
FOREIGN KEY (recept_id) REFERENCES recept(recept_id));

CREATE TABLE produktbatchkomponent(
rb_id INTEGER(11),
pb_id INTEGER(11),
opr_id INTEGER(8),
tara DOUBLE,
netto DOUBLE,
PRIMARY KEY (rb_id, pb_id),
FOREIGN KEY (rb_id) REFERENCES raavarebatch(rb_id),
FOREIGN KEY (pb_id) REFERENCES produktbatch(pb_id),
FOREIGN KEY (opr_id) REFERENCES operatoer(opr_id)
);

INSERT INTO rettighed(rettighedsniveau, stilling) VALUES
(0, 'InAktiv'),
(1, 'Administrator'),
(2, 'Farmaceut'),
(3, 'Værkfører'),
(4, 'Operatør');

INSERT INTO operatoer(opr_id, opr_navn, ini, cpr, password, rettighedsniveau) VALUES
(1, 'Angelo A', 'AA', '070770-7007', 'lKje4fa', 4),
(2, 'Antonella B', 'AB', '080880-8008', 'atoJ21v', 3),
(3, 'Luigi C', 'LC', '090990-9009', 'jEfm5aQ', 3),
(4, 'Don Juan', 'DJ', '100289-1021', 'iloveyou', 1),
(5, 'Jarl Hansen', 'JH', '101010-1010', 'hansengud', 0);

INSERT INTO raavare(raavare_id, raavare_navn, leverandoer) VALUES
(1, 'dej', 'Wawelka'),
(2, 'tomat', 'Knoor'),
(3, 'tomat', 'Veaubais'),
(4, 'tomat', 'Franz'),
(5, 'ost', 'Ost og Skinke A/S'),
(6, 'skinke', 'Ost og Skinke A/S'),
(7, 'champignon', 'Igloo Frostvarer');

INSERT INTO raavarebatch(rb_id, raavare_id, maengde) VALUES
(1, 1, 1000),
(2, 2, 300),
(3, 3, 300),
(4, 5, 100),
(5, 5, 100), 
(6, 6, 100),
(7, 7, 100);

INSERT INTO recept(recept_id, recept_navn) VALUES
(1, 'margherita'),
(2, 'prosciutto'),
(3, 'capricciosa');

INSERT INTO receptkomponent(recept_id, raavare_id, nom_netto, tolerance) VALUES
(1, 1, 5.0, 0.1),
(1, 2, 2.0, 0.1),
(1, 5, 2.0, 0.1),
(2, 1, 5.0, 0.1),
(2, 3, 2.0, 0.1),  
(2, 5, 1.5, 0.1),
(2, 6, 1.5, 0.1),
(3, 1, 5.0, 0.1),
(3, 4, 1.5, 0.1),
(3, 5, 1.5, 0.1),
(3, 6, 1.0, 0.1),
(3, 7, 1.0, 0.1);

INSERT INTO produktbatch(pb_id, recept_id, status) VALUES
(1, 1, 2), 
(2, 1, 2),
(3, 2, 2),
(4, 3, 1),
(5, 3, 0);


INSERT INTO produktbatchkomponent(pb_id, rb_id, tara, netto, opr_id) VALUES
(1, 1, 0.5, 5.05, 1),
(1, 2, 0.5, 2.03, 1),
(1, 4, 0.5, 1.98, 1),
(2, 1, 0.5, 5.01, 2),
(2, 2, 0.5, 1.99, 2),
(2, 5, 0.5, 1.47, 2),
(3, 1, 0.5, 5.07, 1),
(3, 3, 0.5, 2.06, 2),
(3, 4, 0.5, 1.55, 1),
(3, 6, 0.5, 1.53, 2),
(4, 1, 0.5, 5.02, 3),
(4, 5, 0.5, 1.57, 3),
(4, 6, 0.5, 1.03, 3),
(4, 7, 0.5, 0.99, 3);