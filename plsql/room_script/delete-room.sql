CREATE OR REPLACE PROCEDURE delete_room(
    p_id IN NUMBER --the id of the room
) AS
BEGIN
    --deleting the room
    DELETE FROM chambre 
    WHERE id_chambre = p_id;
    COMMIT;
END;
/