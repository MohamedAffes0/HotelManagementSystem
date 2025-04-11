create table employe(
id int PRIMARY KEY,
nom VARCHAR2(20),
prenom VARCHAR2(20),
mail VARCHAR2(30) not null,
mdp varchar2(30) not null,
is_admin NUMBER(1) DEFAULT 0, -- 0 for false, 1 for true
is_active NUMBER(1) DEFAULT 0, -- 0 for inactive, 1 for active
CONSTRAINT check_active check (is_active IN (0, 1)), --le statut de l'employé--
CONSTRAINT check_is_admin check (is_admin IN (0, 1)), --le statut de l'employé--
CONSTRAINT check_email CHECK (mail LIKE '%_@__%.__%')  --le format de l'email--
);

create table client_hotel(
cin int primary key,
nom VARCHAR2(20),
prenom VARCHAR2(20),
mail VARCHAR2(30),
CONSTRAINT check_email CHECK (mail IS NULL OR mail LIKE '%_@__%.__%') --le format de l'email--
);

CREATE table chambre(
    id_chambre int primary key,
    type_chambre VARCHAR2(20),--simple, double, suite--
    etage int check (etage >= 0),--l'étage de la chambre--
    nb_personnes int,--le nombre de personnes--
    prix float,--le prix de la chambre--
    etat NUMBER(1), -- 0 for libre, 1 for occupée --, 2 for maintenance --
    CONSTRAINT check_etat CHECK (etat IN (0, 1, 2)) --l'état de la chambre--
);

create table reservation(
    id_reservation int primary key,
    date_debut date,
    date_fin date,
    paid NUMBER(1), -- 0 for unpaid, 1 for paid --
    employe int,--l'employé qui a fait la réservation--
    client_hotel int,--le client qui a fait la réservation--
    chambre int,--la chambre réservée--
    CONSTRAINT fk_employe FOREIGN KEY (employe) REFERENCES employe(id) ON DELETE SET NULL,
    CONSTRAINT fk_client_hotel FOREIGN KEY (client_hotel) REFERENCES client_hotel(cin) ON DELETE SET NULL,
    CONSTRAINT fk_chambre FOREIGN KEY (chambre) REFERENCES chambre(id_chambre) on delete cascade,
    CONSTRAINT check_date CHECK (date_debut <= date_fin), --la date de début doit être inférieure à la date de fin--
    CONSTRAINT check_paid CHECK (paid IN (0, 1)) --le statut de la réservation--
);

-- cration de l'utilisateur root
INSERT INTO employe VALUES(1, 'root', 'root', 'root@gmail.com', '1234', 1, 1);