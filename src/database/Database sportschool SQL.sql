DROP DATABASE sportschool;
CREATE DATABASE IF NOT EXISTS sportschool;

-- Create tables in sportschool database
USE sportschool;
SET foreign_key_checks = 0;

CREATE TABLE IF NOT EXISTS abonnementsvormen (
    abonnementsvorm_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    abonnementsnaam VARCHAR(50) NOT NULL,
    beschrijving VARCHAR(255),
    prijs DECIMAL(15,2) NOT NULL,
	
    PRIMARY KEY (abonnementsvorm_id)
);

CREATE TABLE IF NOT EXISTS abonnementen (
    abonnement_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    akkoord_voorwaarden TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
    abonnement_start_datum DATETIME NOT NULL DEFAULT NOW(),
    abonnement_eind_datum DATETIME NOT NULL,
    abonnementsvorm_id INT UNSIGNED,
    account_id INT UNSIGNED,
    
	PRIMARY KEY (abonnement_id)
);

CREATE TABLE IF NOT EXISTS klanten (
    klant_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    voornaam VARCHAR(255) NOT NULL,
    tussenvoegsel varchar(20),
    achternaam VARCHAR(255) NOT NULL,
    geslacht ENUM ('Man', 'Vrouw') NOT NULL,
    postcode VARCHAR(10) NOT NULL,
    huisnummer VARCHAR(5) NOT NULL,
    account_id INT UNSIGNED,
    abonnement_id INT UNSIGNED,
    begeleider_id INT UNSIGNED,
    betaal_gegevens_id INT UNSIGNED,
    
	PRIMARY KEY (klant_id)
);

CREATE TABLE IF NOT EXISTS persoonlijk_advies (
	persoonlijk_advies_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	onderwerp VARCHAR(255),
    advies VARCHAR(255) NOT NULL,
    klant_id INT UNSIGNED,
    begeleider_id INT UNSIGNED,
    
    PRIMARY KEY(persoonlijk_advies_id)
);

CREATE TABLE IF NOT EXISTS accounts (
	account_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    username VARCHAR(31) NOT NULL,
    passwd VARCHAR(31) NOT NULL,
    email VARCHAR(255) NOT NULL,
    akkoord_voorwaarden TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	klant_id INT UNSIGNED,
    
    PRIMARY KEY (account_id)
);

CREATE TABLE IF NOT EXISTS tags (
	tag_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    tag VARCHAR(255) NOT NULL,
	account_id INT UNSIGNED,
    
    PRIMARY KEY (tag_id)
);

CREATE TABLE IF NOT EXISTS sessies (
	sessie_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    incheck_tijd DATETIME NOT NULL,
    uitcheck_tijd DATETIME NOT NULL DEFAULT current_timestamp,
    sessie_duur TIME NOT NULL, 
    tag_id INT UNSIGNED,
    account_id INT UNSIGNED NOT NULL,
    
    PRIMARY KEY(sessie_id)
    
);

CREATE TABLE IF NOT EXISTS inschrijvingen (
	inschrijving_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    groepsles_id INT UNSIGNED NOT NULL,
    account_id INT UNSIGNED NOT NULL,    
    
    PRIMARY KEY(inschrijving_id)
);

CREATE TABLE IF NOT EXISTS locaties (
	locatie_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    land VARCHAR(255) NOT NULL DEFAULT 'Nederland',
    plaats VARCHAR(255) NOT NULL,
    postcode VARCHAR(10) NOT NULL,
    straat VARCHAR(255) NOT NULL,
    huisnummer VARCHAR(5) NOT NULL,
    
    PRIMARY KEY(locatie_id)
);

CREATE TABLE IF NOT EXISTS groepslessen (
	groepsles_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    groepsles_naam VARCHAR(255) NOT NULL,
    aantal_deelnemers INT UNSIGNED NOT NULL,    
    max_deelnemers INT UNSIGNED NOT NULL,
    datum DATE NOT NULL,
    tijd TIME NOT NULL,
    prijs DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    locatie_id INT UNSIGNED,
    begeleider_id INT UNSIGNED,
    
    PRIMARY KEY(groepsles_id)
);

CREATE TABLE IF NOT EXISTS begeleiders (
	begeleider_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	voornaam VARCHAR(255) NOT NULL,
    tussenvoegsel varchar(20),
    achternaam VARCHAR(255) NOT NULL,
    geslacht ENUM ('Man', 'Vrouw') NOT NULL,
	rol VARCHAR(255) NOT NULL DEFAULT 'Medewerker',
    specialisatie VARCHAR(255),
    contract_start_datum DATE,
	contract_eind_datum DATE,

    PRIMARY KEY(begeleider_id)
);

CREATE TABLE IF NOT EXISTS betaal_gegevens (
	betaal_gegevens_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	IBAN VARCHAR(255) NOT NULL,

    PRIMARY KEY(betaal_gegevens_id)
);


-- Foreign keys
ALTER TABLE abonnementen
ADD FOREIGN KEY (abonnementsvorm_id) REFERENCES abonnementsvormen(abonnementsvorm_id),
ADD FOREIGN KEY (account_id) REFERENCES accounts(account_id);

ALTER TABLE klanten
ADD FOREIGN KEY (account_id) REFERENCES accounts(account_id),
ADD FOREIGN KEY (abonnement_id) REFERENCES abonnementen(abonnement_id),
ADD FOREIGN KEY (begeleider_id) REFERENCES begeleiders(begeleider_id),
ADD FOREIGN KEY (betaal_gegevens_id) REFERENCES betaal_gegevens(betaal_gegevens_id);

ALTER TABLE persoonlijk_advies
ADD FOREIGN KEY (begeleider_id) REFERENCES begeleiders(begeleider_id),
ADD FOREIGN KEY (klant_id) REFERENCES klanten(klant_id);

ALTER TABLE accounts
ADD FOREIGN KEY (klant_id) REFERENCES klanten(klant_id);

ALTER TABLE tags
ADD FOREIGN KEY (account_id) REFERENCES accounts(account_id);

ALTER TABLE sessies
ADD FOREIGN KEY (account_id) REFERENCES accounts(account_id),
ADD FOREIGN KEY (tag_id) REFERENCES tags(tag_id);

ALTER TABLE inschrijvingen
ADD FOREIGN KEY (groepsles_id) REFERENCES groepslessen(groepsles_id),
ADD FOREIGN KEY (account_id) REFERENCES accounts(account_id);

ALTER TABLE groepslessen
ADD FOREIGN KEY (begeleider_id) REFERENCES begeleiders(begeleider_id),
ADD FOREIGN KEY (locatie_id) REFERENCES locaties(locatie_id);


-- Default subscriptions
INSERT INTO abonnementsvormen (abonnementsnaam, beschrijving, prijs)
VALUES 	('Jeugd abonnement', 'Dit abonnement mag alleen gebruikt worden door jongeren van 14 t/m 17 jaar.', 19.99),
		('Volwassenen abonnement', 'Dit abonnement mag alleen gebruikt worden door volwassenen van 18 t/m 54 jaar.', 24.99),
		('Senioren abonnement', 'Dit abonnement mag alleen gebruikt worden door senioren van 55+.', 19.99),
		('Twee dagen per week abonnement', 'Dit abonnement mag twee dagen per week gebruikt worden.', 9.99),
        ('Personal training abonnement', 'Dit is ons volledige pakket, je krijgt 10 uur personal training per week', 29.99),
        
        ('Winterstop abonnement', 'Dit abonnement mag alleen gebruikt worden tijdens de winterstop met vertoon van een geldige clubkaart.', 19.99),
		('Zomer abonnement', 'Dit abonnement mag alleen gebruikt worden tijdens de zomer.', 19.99),
		('Kwartaal abonnement', 'Dit abonnement mag alleen gebruikt worden door jongeren van 14 t/m 18 jaar.', 29.99),
		('Studenten abonnement', 'Dit abonnement mag alleen gebruikt worden bij vertoon van geldige studentenkaart.', 14.99),
		('Nieuwjaars abonnement', 'Dit abonnement is alleen geldig in januari en febuari', 9.99);
        
INSERT INTO klanten (voornaam, achternaam, geslacht, postcode, huisnummer, account_id, abonnement_id, begeleider_id)
VALUES ('Youri', 'Mulder', 'Man', '6721BT', '18A', 1, null, 1),
('Johan', 'Mulder', 'Man', '6721BT', '18A', 2, null, 2);

INSERT INTO accounts (username, passwd, email, akkoord_voorwaarden, klant_id)
VALUES ('Youri', 'Mulder', 'Youri@demuldertjes.nl', 1, 1),
('Johan', 'Mulder', 'test@live.nl', 1, 2);

INSERT INTO begeleiders (voornaam, tussenvoegsel, achternaam, geslacht, rol, specialisatie, contract_start_datum, contract_eind_datum)
VALUES ('Benno', null , 'Bakker', 'Man', 'Eigenaar', 'Groepslessen', null, null),
('Arnoud', 'van den',  'Brink', 'Man', 'Medewerker', 'Gewichten', CURRENT_TIMESTAMP, '2019-05-11'),
('Sophie', null, 'Gerritsen', 'Vrouw', 'Medewerker', 'Looptechniek', '2013-10-10', null),
('Marit', null, 'Knoops', 'Vrouw', 'Medewerker', 'Voeding', '2003-02-01', null),
('Gerrit', null , 'Genus', 'Man', 'Medewerker', 'Groepslessen', null, null),
('Gerald', 'den',  'Dick', 'Man', 'Medewerker', 'Cardio', CURRENT_TIMESTAMP, '2019-05-11'),
('Joost', null, 'Janssen', 'Man', 'Medewerker', 'Algemeen', '2005-11-10', null),
('Michelle', null, 'Koops', 'Vrouw', 'Medewerker', 'invalide', '1999-01-01', null),('Benno', null , 'Bakker', 'Man', 'Eigenaar', 'Groepslessen', null, null),
('Elisa', 'van den',  'Broek', 'Vrouw', 'Medewerker', 'Top sport', CURRENT_TIMESTAMP, '2020-05-11'),
('Sjon', 'De', 'Man', 'Man', 'Medewerker', 'Gewichten', '1997-03-04', null),
('John', null, 'Miller', 'Man', 'Medewerker', 'Voeding', '1998-04-05', null);

INSERT INTO tags (tag, account_id)
VALUES ("126, 13, 134, 137, 124", 1),
("3, 127, 139, 171, 92", 2);

INSERT INTO sessies (incheck_tijd, uitcheck_tijd, sessie_duur, account_id)
VALUES (NOW(), DATE_ADD(NOW(),  INTERVAL 1 HOUR), '01:00:00', 1),
(NOW(), DATE_ADD(NOW(),  INTERVAL 2 HOUR), '02:00:00', 1),
(NOW(), DATE_ADD(NOW(),  INTERVAL 30 MINUTE), '00:30:00', 1);

INSERT INTO persoonlijk_advies (onderwerp, advies, klant_id, begeleider_id)
VALUES ('Gewichten', 'Je moet meer vannuit je benen tillen.',  1, 2),
('Cardio', 'Je moet minder op je tenen lopen.', 1, 3);
