CREATE OR REPLACE PROCEDURE add_employe (
    p_nom VARCHAR2,
    p_prenom VARCHAR2,
    p_mail VARCHAR2,
    p_mdp VARCHAR2,
    p_is_admin NUMBER DEFAULT 0,
    p_is_active NUMBER DEFAULT 0
) AS
BEGIN
    -- Insère un employé avec l'ID généré par la séquence
    INSERT INTO employe (id, nom, prenom, mail, mdp, is_admin, is_active)
    VALUES (employe_id_seq.NEXTVAL, p_nom, p_prenom, p_mail, p_mdp, p_is_admin, p_is_active);
    COMMIT;
END;
/

