CREATE OR REPLACE PROCEDURE add_employe (
    p_nom IN VARCHAR2 DEFAULT NULL,--name of the employe--
    p_prenom IN VARCHAR2 DEFAULT NULL,--last name of the employe--
    p_mail IN VARCHAR2,--mail of the employe--
    p_mdp IN VARCHAR2,--password of the employe--
    p_is_admin IN NUMBER DEFAULT 0,--equals 0 if the employe isn't an admin--
    p_is_active IN NUMBER DEFAULT 0--equals 0 if the account isn't active--
) AS
BEGIN
    --insert an employe in the database--
    INSERT INTO employe (id, nom, prenom, mail, mdp, is_admin, is_active)
    VALUES (employe_id_seq.NEXTVAL, p_nom, p_prenom, p_mail, p_mdp, p_is_admin, p_is_active);
    COMMIT;
END;
/

