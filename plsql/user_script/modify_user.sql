CREATE OR REPLACE PROCEDURE modify_user(
    p_id IN NUMBER, --user id 
    p_admin IN NUMBER DEFAULT 0, --check if the user is admin
    p_active IN NUMBER DEFAULT 0 --check if the account is active
) AS
BEGIN
    --modifying and updating the user table with the information given in the parameters
    UPDATE employe 
    set is_admin = p_admin, is_active = p_active
    WHERE id = p_id;
    COMMIT;
END;
/