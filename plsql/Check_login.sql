CREATE OR REPLACE PROCEDURE check_login (
    p_email IN VARCHAR2,--email of the employe--
    p_password IN VARCHAR2,--password of the employe--
    p_exists OUT NUMBER,--equals 1 if the employe exists--
    p_active OUT NUMBER,--equals 1 if the account is active--
    p_admin OUT NUMBER--equals 1 if the employe is an admin--
) AS
BEGIN
    BEGIN
    --check if the employe exists in the database--
        SELECT COUNT(*) INTO p_exists
        FROM EMPLOYE
        WHERE mail = p_email AND mdp = p_password;

        --check if the employe is an admin and if the account is active--
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
--handle other possible exceptions--
        WHEN OTHERS THEN
            p_exists := 0;
            p_active := 0;
            p_admin := 0;
END;
/
