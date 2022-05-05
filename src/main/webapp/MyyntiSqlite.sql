--
-- File generated with SQLiteStudio v3.3.3 on pe huhti 8 14:32:11 2022
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Asiakkaat
CREATE TABLE Asiakkaat (
    Asiakas_id INTEGER       PRIMARY KEY AUTOINCREMENT,
    Etunimi    VARCHAR (50)  NOT NULL,
    Sukunimi   VARCHAR (50)  NOT NULL,
    Puhelin    VARCHAR (20)  NOT NULL,
    Sposti     VARCHAR (100) NOT NULL
);


-- Index: 
CREATE INDEX "" ON Asiakkaat (
    Asiakas_id
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
