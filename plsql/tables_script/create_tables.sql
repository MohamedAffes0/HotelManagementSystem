create table employe(
    id int PRIMARY KEY,
    nom VARCHAR2(20),
    prenom VARCHAR2(20),
    mail VARCHAR2(30) not null,
    mdp varchar2(30) not null, -- mot de passe de l'employé--
    is_admin NUMBER(1) DEFAULT 0, -- 0 pour false, 1 pour true
    is_active NUMBER(1) DEFAULT 0, -- 0 pour inactive, 1 pour active
    CONSTRAINT check_active check (is_active IN (0, 1)),
    CONSTRAINT check_is_admin check (is_admin IN (0, 1)),
    CONSTRAINT check_email_employe CHECK (mail LIKE '%_@__%.__%')  --le format de l'email--
);

create table client_hotel(
    cin int primary key,
    nom VARCHAR2(20),
    prenom VARCHAR2(20),
    mail VARCHAR2(30),
    CONSTRAINT check_email_client CHECK (mail IS NULL OR mail LIKE '%_@__%.__%') --le format de l'email--
);

CREATE table chambre(
    id_chambre int primary key,
    type_chambre VARCHAR2(20),--simple, double, suite--
    etage int check (etage >= 0),--l'étage de la chambre--
    nb_personnes int,--le nombre de personnes--
    prix float --le prix de la chambre--
);

create table reservation(
    id_reservation int primary key,
    date_debut date,
    date_fin date,
    paid NUMBER(1), -- 0 pour non payé, 1 pour payé --
    employe int, --l'employé qui a fait la réservation--
    client_hotel int DEFAULT NULL, --le client qui a fait la réservation | si null alors reservation de maintenance--
    chambre int, --la chambre réservée--
    CONSTRAINT fk_employe FOREIGN KEY (employe) REFERENCES employe(id) ON DELETE SET NULL,
    CONSTRAINT fk_client_hotel FOREIGN KEY (client_hotel) REFERENCES client_hotel(cin) ON DELETE SET NULL,
    CONSTRAINT fk_chambre FOREIGN KEY (chambre) REFERENCES chambre(id_chambre) on delete cascade, -- suprimer la reservation si la chambre est supprimée--
    CONSTRAINT check_date CHECK (date_debut <= date_fin), --la date de début doit être inférieure à la date de fin--
    CONSTRAINT check_paid CHECK (paid IN (0, 1)) --le statut de la réservation--
);

-- cration de l'utilisateur root
INSERT INTO employe VALUES(1, 'root', 'root', 'root@gmail.com', '1234', 1, 1);