create table log_client(
    id_client_delete int primary key,
    cin           INT, 
    nom           VARCHAR2(20), --client last name
    prenom        VARCHAR2(20), --client first name
    mail          VARCHAR2(30), --client mail
    user_action varchar2(30), --the user who deleted the client
    action_date   DATE --when the client was deleted
);

create table log_chambre
(
    id_chambre_deleted int primary key,
    type_chambre VARCHAR2(20), --room type
    etage int check (etage >= 0), --room floor
    nb_personnes int,--maximum occupancy
    prix float, --price of a night 
    etat NUMBER(1),--room status
    user_action varchar2(30),--the user who deleted the client
    action_date   DATE--when the client was deleted
);

create table log_employe(
    id_emp_deleted int PRIMARY KEY,
    nom VARCHAR2(20),--employee last name
    prenom VARCHAR2(20),--employee first name
    mail VARCHAR2(30) ,--employee mail
    mdp varchar2(30) ,--employee password
    is_admin NUMBER(1),--check if the employee was an admin
    is_active NUMBER(1),--check if the account was active
    user_action varchar2(30),--the user who deleted the client
    action_date   DATE--when the client was deleted

);

create table log_reservation(
    id_reservation_deleted int primary key,
    date_debut date,--when it started
    date_fin date,--when it ended
    paid NUMBER(1),--check if it was paid
    employe int,
    client_hotel int,
    chambre int,
    user_action varchar2(30),--the user who deleted the client
    action_date   DATE,--when the client was deleted
    CONSTRAINT fk_log_employe FOREIGN KEY (employe) REFERENCES log_employe(id_emp_deleted),
    CONSTRAINT fk_log_client_hotel FOREIGN KEY (client_hotel) REFERENCES log_client(id_client_deleted),
    CONSTRAINT fk_log_chambre FOREIGN KEY (chambre) REFERENCES log_chambre(id_chambre_deleted)
);