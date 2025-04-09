CREATE OR REPLACE PROCEDURE add_employe (
    p_cin NUMBER,
    p_nom VARCHAR2,
    p_prenom VARCHAR2,
    p_mail VARCHAR2,
    p_mdp VARCHAR2,
    p_is_admin NUMBER DEFAULT 0,
    p_is_active NUMBER DEFAULT 0
) AS
BEGIN
    INSERT INTO employe (cin, nom, prenom, mail, mdp, is_admin, is_active)
    VALUES (p_cin, p_nom, p_prenom, p_mail, p_mdp, p_is_admin, p_is_active);
    COMMIT;
END;
/
