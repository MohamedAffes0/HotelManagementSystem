create or replace trigger delete_reservation
after delete on reservation
for each row 
BEGIN
    insert into log_reservation(id_reservation_deleted,date_debut,date_fin,
    paid,employe, client_hotel,chambre,user_action,action_date)
    values (log_reservation_seq.NEXTVAL,:old.date_debut,:old.date_fin,:old.paid,:old.employe,
    :old.client_hotel,:old.chambre,user,sysdate);

end;

