CREATE OR REPLACE TRIGGER delete_client
AFTER DELETE ON client_hotel
FOR EACH ROW
BEGIN
    INSERT INTO log_client (id_client_deleted,cin, nom, prenom, mail, user_action, action_date)
    VALUES (log_client_seq.NEXTVAL,:OLD.cin, :OLD.nom, :OLD.prenom, :OLD.mail,USER, SYSDATE);
END;