CREATE OR REPLACE PROCEDURE modify_user(
    p_id IN NUMBER,--id of the user--
    p_admin IN NUMBER DEFAULT 0,--equals 1 if the user is an admin--
    p_active IN NUMBER DEFAULT 0--equals 1 if the account is active--
) AS
BEGIN
    --update the 'is admin' and 'is_active' attributes for the user with p_id--
    UPDATE employe 
    set is_admin = p_admin, is_active = p_active
    WHERE id = p_id;
    COMMIT;
END;
/
