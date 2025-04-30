-- suppression of the user
CREATE OR REPLACE PROCEDURE delete_user(
    p_id IN NUMBER
) AS
BEGIN
    DELETE FROM employe 
    WHERE id = p_id;
    COMMIT;
END;
/