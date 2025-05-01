CREATE OR REPLACE PROCEDURE delete_user(
    p_id IN NUMBER --user id
) AS
BEGIN
    --deleting the user from the database
    DELETE FROM employe 
    WHERE id = p_id;
    COMMIT;
END;
/