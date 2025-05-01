CREATE OR REPLACE PROCEDURE add_employe (
    p_nom IN VARCHAR2 DEFAULT NULL, --employee last name
    p_prenom IN VARCHAR2 DEFAULT NULL, --employee first name
    p_mail IN VARCHAR2, --employee mail
    p_mdp IN VARCHAR2, --employee password
    p_is_admin IN NUMBER DEFAULT 0, --check if the employee is an admin
    p_is_active IN NUMBER DEFAULT 0 --check if the account is active
) AS
BEGIN
    -- Insère un employé avec l'ID généré par la séquence
    INSERT INTO employe (id, nom, prenom, mail, mdp, is_admin, is_active)
    VALUES (employe_id_seq.NEXTVAL, p_nom, p_prenom, p_mail, p_mdp, p_is_admin, p_is_active);
    COMMIT;
END;
/

