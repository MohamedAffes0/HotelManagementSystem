create table log_client(
    id_client_delete int primary key,
    cin           INT,
    nom           VARCHAR2(20),
    prenom        VARCHAR2(20),
    mail          VARCHAR2(30),
    user_action varchar2(30),
    action_date   DATE
);

create table log_chambre
(
    id_chambre_deleted int primary key,
    type_chambre VARCHAR2(20),
    etage int check (etage >= 0),
    nb_personnes int,
    prix float,
    etat NUMBER(1),
    user_action varchar2(30),
    action_date   DATE
);

create table log_employe(
    id_emp_deleted int PRIMARY KEY,
    nom VARCHAR2(20),
    prenom VARCHAR2(20),
    mail VARCHAR2(30) ,
    mdp varchar2(30) ,
    is_admin NUMBER(1),
    is_active NUMBER(1),
    user_action varchar2(30),
    action_date   DATE

);

create table log_reservation(
    id_reservation_deleted int primary key,
    date_debut date,
    date_fin date,
    paid NUMBER(1),
    employe int,
    client_hotel int,
    chambre int,
    user_action varchar2(30),
    action_date   DATE,
    CONSTRAINT fk_log_employe FOREIGN KEY (employe) REFERENCES log_employe(id_emp_deleted),
    CONSTRAINT fk_log_client_hotel FOREIGN KEY (client_hotel) REFERENCES log_client(id_client_deleted),
    CONSTRAINT fk_log_chambre FOREIGN KEY (chambre) REFERENCES log_chambre(id_chambre_deleted)
);