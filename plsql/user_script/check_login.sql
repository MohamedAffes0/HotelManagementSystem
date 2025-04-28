CREATE OR REPLACE PROCEDURE check_login (
    p_email IN VARCHAR2,
    p_password IN VARCHAR2,
    p_id OUT NUMBER,
    p_active OUT NUMBER,
    p_admin OUT NUMBER
) AS
BEGIN
    BEGIN
        p_id := 0; -- p_id = 0 lorsque l'utilisateur n'existe pas
        SELECT id, is_admin, is_active INTO p_id, p_admin, p_active
        FROM EMPLOYE
        WHERE mail = p_email AND mdp = p_password;
    END;
    EXCEPTION
        WHEN no_data_found THEN
            p_id := 0;
            p_active := 0;
            p_admin := 0;
        WHEN OTHERS THEN
            p_id := 0;
            p_active := 0;
            p_admin := 0;
END;
/