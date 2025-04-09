CREATE OR REPLACE PROCEDURE modify_user(
    p_id IN NUMBER,
    p_admin IN NUMBER DEFAULT 0,
    p_active IN NUMBER DEFAULT 0
) AS
BEGIN
    UPDATE employe 
    set is_admin = p_admin, is_active = p_active
    WHERE id = p_id;
    COMMIT;
END;
/