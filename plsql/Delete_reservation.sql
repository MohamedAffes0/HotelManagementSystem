CREATE OR REPLACE PROCEDURE delete_reservation(
    p_id IN NUMBER --id of the reservation--
) AS
BEGIN
    --delete the reservation with p_id from the database--
    DELETE FROM reservation
    WHERE id_reservation = p_id;
    COMMIT;
END;
/
