CREATE OR REPLACE PROCEDURE check_login (
    p_email IN VARCHAR2, --employee mail
    p_password IN VARCHAR2,--employee password
    p_exists OUT NUMBER,--check if the employee exists in the database
    p_active OUT NUMBER, --check if the account is active
    p_admin OUT NUMBER --check if the user is admin
) AS
BEGIN
    BEGIN
        SELECT COUNT(*) INTO p_exists
        FROM EMPLOYE
        WHERE mail = p_email AND mdp = p_password;
        
        SELECT is_admin, is_active INTO p_admin, p_active
        FROM EMPLOYE
        WHERE mail = p_email AND mdp = p_password;

        --p_exists equals 1 if the user exists, else 0
        IF p_exists > 0 THEN
            p_exists := 1;
        ELSE
            p_exists := 0;
        END IF;
    END;
    EXCEPTION
        --when no data is found
        WHEN no_data_found THEN
            p_exists := 0;
            p_active := 0;
            p_admin := 0;
        --handling other exceptions
        WHEN OTHERS THEN
            p_exists := 0;
            p_active := 0;
            p_admin := 0;
END;
/