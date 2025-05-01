CREATE OR REPLACE PROCEDURE delete_reservation(
    p_id IN NUMBER --id of the reservation that we want to delete from the database
) AS
BEGIN
    --deleting the reservation
    DELETE FROM reservation
    WHERE id_reservation = p_id;
    COMMIT;
END;
/