-- retourner tous les utilisateurs

CREATE OR REPLACE PROCEDURE get_all_users(
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT id, nom, prenom, mail, mdp, is_admin, is_active
        FROM employe;
END;
/