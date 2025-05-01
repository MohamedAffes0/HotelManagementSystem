create or replace trigger delete_employe
after delete on employe
for each ROW
BEGIN
    insert into log_employe (
        id_emp_deleted, nom,prenom,mail,mdp,
        is_admin,is_active,user_action,action_date
    )
    values(
        log_employe_seq.NEXTVAL, :old.nom, :old.prenom, :old.mail,
        :old.mdp,:old.is_admin,:old.is_active,user,sysdate
        );
end;
