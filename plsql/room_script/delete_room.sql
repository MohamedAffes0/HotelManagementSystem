CREATE OR REPLACE PROCEDURE delete_room(
    p_id IN NUMBER
) AS
BEGIN
    DELETE FROM chambre 
    WHERE id_chambre = p_id;
    COMMIT;
END;
/