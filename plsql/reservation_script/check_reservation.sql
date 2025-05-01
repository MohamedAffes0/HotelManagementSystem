CREATE OR REPLACE PROCEDURE check_reservation (
    p_id_chambre IN NUMBER, --the id of the room
    p_id_reservation_acutel IN NUMBER, --the id of the reservation
    p_cursor OUT SYS_REFCURSOR --oracle cursor that returns the reservations in a dynamic way
) AS
BEGIN
    --check if the room is already reserved
    OPEN p_cursor FOR
        SELECT date_debut, date_fin
        FROM reservation
        WHERE chambre = p_id_chambre AND id_reservation != p_id_reservation_acutel;
END;
/