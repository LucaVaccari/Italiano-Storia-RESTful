drop database `ksv-links`;
create database `ksv-links`;

create table autori (
    id_autore integer PRIMARY KEY AUTO_INCREMENT,
    nome varchar(20) NOT NULL,
    cognome varchar(20) NOT NULL,
    data_nascita date,
    data_morte date,
    luogo_nascita varchar(20),
    luogo_morte varchar(20),
    vita MEDIUMTEXT
);

create table testi (
    id_testo integer PRIMARY KEY AUTO_INCREMENT,
    titolo varchar(20) NOT NULL,
    data_pubblicazione date,
    espressione_linguistica ENUM("prosa", "poesia"),
    corrente varchar(20),
    descrizione MEDIUMTEXT,
    id_autore integer REFERENCES autori(id_autore)
);

create table argomenti (
    id_argomento integer PRIMARY KEY AUTO_INCREMENT,
    nome varchar(30) NOT NULL,
    data_inizio date,
    data_fine date,
    descrizione MEDIUMTEXT,
    luogo varchar(30)
);

create table autori_argomenti (
    id_autore integer,
    id_argomento integer,
    PRIMARY KEY (id_autore, id_argomento),
    FOREIGN KEY (id_autore) REFERENCES autori(id_autore),
    FOREIGN KEY (id_argomento) REFERENCES argomenti(id_argomento)
);

create table testi_argomenti (
    id_testo integer,
    id_argomento integer,
    PRIMARY KEY (id_testo, id_argomento),
    FOREIGN KEY (id_testo) REFERENCES testi(id_testo),
    FOREIGN KEY (id_argomento) REFERENCES argomenti(id_argomento)
);

create table argomenti_argomenti (
    id_argomento1 integer,
    id_argomento2 integer,
    PRIMARY KEY (id_argomento1, id_argomento2),
    FOREIGN KEY (id_argomento1) REFERENCES argomenti(id_argomento),
    FOREIGN KEY (id_argomento2) REFERENCES argomenti(id_argomento)
)