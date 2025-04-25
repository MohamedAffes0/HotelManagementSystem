create or replace trigger delete_chambre
after delete on chambre
for each ROW
BEGIN
insert into log_chambre(
    id_chambre_deleted,type_chambre,etage,nb_personnes,
prix,etat,user_action,action_date
)
values (
    log_chambre_seq.NEXTVAL,:old.type_chambre,:old.etage,
:old.nb_personnes,:old.prix,:old.etat,user,sysdate
);
end;