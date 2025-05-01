CREATE OR REPLACE PROCEDURE get_all_users(
    p_cursor OUT SYS_REFCURSOR
    --oracle cursor that returns the users in a dynamic way
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT id, nom, prenom, mail, mdp, is_admin, is_active
        --user information
        FROM employe;
END;
/