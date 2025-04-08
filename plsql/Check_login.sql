CREATE OR REPLACE PROCEDURE check_login (
    p_email IN VARCHAR2,
    p_password IN VARCHAR2,
    p_exists OUT NUMBER
) AS
BEGIN
    SELECT COUNT(*) INTO p_exists
    FROM EMPLOYE
    WHERE mail = p_email AND mdp = p_password;
    
    IF p_exists > 0 THEN
        p_exists := 1;
    ELSE
        p_exists := 0;
    END IF;
END;
/