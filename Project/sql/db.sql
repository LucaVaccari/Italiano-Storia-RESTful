DROP DATABASE `ksv-links`;
CREATE DATABASE `ksv-links`;
CREATE TABLE autori (
    id_autore INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(20) NOT NULL,
    cognome VARCHAR(20) NOT NULL,
    data_nascita DATE,
    data_morte DATE,
    luogo_nascita VARCHAR(20),
    luogo_morte VARCHAR(20),
    vita MEDIUMTEXT
);
CREATE TABLE lingue (
    id_lingua INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(10) NOT NULL
);
CREATE TABLE testi (
    id_testo INTEGER PRIMARY KEY AUTO_INCREMENT,
    titolo VARCHAR(20) NOT NULL,
    data_pubblicazione DATE,
    espressione_linguistica ENUM("prosa", "poesia"),
    corrente VARCHAR(20),
    descrizione MEDIUMTEXT,
    id_autore INTEGER REFERENCES autori(id_autore),
    id_lingua INTEGER REFERENCES lingue(id_lingua)
);
CREATE TABLE argomenti (
    id_argomento INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    data_inizio DATE,
    data_fine DATE,
    descrizione MEDIUMTEXT,
    luogo VARCHAR(30)
);
CREATE TABLE autori_argomenti (
    id_autore INTEGER,
    id_argomento INTEGER,
    PRIMARY KEY (id_autore, id_argomento),
    FOREIGN KEY (id_autore) REFERENCES autori(id_autore),
    FOREIGN KEY (id_argomento) REFERENCES argomenti(id_argomento)
);
CREATE TABLE testi_argomenti (
    id_testo INTEGER,
    id_argomento INTEGER,
    PRIMARY KEY (id_testo, id_argomento),
    FOREIGN KEY (id_testo) REFERENCES testi(id_testo),
    FOREIGN KEY (id_argomento) REFERENCES argomenti(id_argomento)
);
CREATE TABLE argomenti_argomenti (
    id_argomento1 INTEGER,
    id_argomento2 INTEGER,
    PRIMARY KEY (id_argomento1, id_argomento2),
    FOREIGN KEY (id_argomento1) REFERENCES argomenti(id_argomento),
    FOREIGN KEY (id_argomento2) REFERENCES argomenti(id_argomento)
);