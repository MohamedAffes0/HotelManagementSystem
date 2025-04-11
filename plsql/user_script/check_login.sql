CREATE OR REPLACE PROCEDURE check_login (
    p_email IN VARCHAR2,
    p_password IN VARCHAR2,
    p_exists OUT NUMBER,
    p_active OUT NUMBER,
    p_admin OUT NUMBER
) AS
BEGIN
    BEGIN
        SELECT COUNT(*) INTO p_exists
        FROM EMPLOYE
        WHERE mail = p_email AND mdp = p_password;
        
        SELECT is_admin, is_active INTO p_admin, p_active
        FROM EMPLOYE
        WHERE mail = p_email AND mdp = p_password;

        IF p_exists > 0 THEN
            p_exists := 1;
        ELSE
            p_exists := 0;
        END IF;
    END;
    EXCEPTION
        WHEN no_data_found THEN
            p_exists := 0;
            p_active := 0;
            p_admin := 0;
        WHEN OTHERS THEN
            p_exists := 0;
            p_active := 0;
            p_admin := 0;
END;
/