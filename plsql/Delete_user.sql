CREATE OR REPLACE PROCEDURE delete_user(
    p_id IN NUMBER --id of the user--
) AS
BEGIN
    --delete the employe with p_id from the database--
    DELETE FROM employe 
    WHERE id = p_id;
    COMMIT;
END;
/
