CREATE OR REPLACE PROCEDURE check_reservation (
    p_id_chambre IN NUMBER,--id of the room--
    p_cursor OUT SYS_REFCURSOR--the cursor that will return the data regarding the reservation dynamically--
) AS
BEGIN
    OPEN p_cursor FOR
    --check if a reservation for a certain room during a certain period has already been made--
        SELECT date_debut, date_fin
        FROM reservation
        WHERE chambre = p_id_chambre;
END;
/
