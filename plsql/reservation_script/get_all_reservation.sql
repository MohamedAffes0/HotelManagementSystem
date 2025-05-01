CREATE OR REPLACE PROCEDURE get_all_reservation(
    p_cursor OUT SYS_REFCURSOR
    --oracle cursor that returns the reservations in a dynamic way
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT id_reservation, date_debut, date_fin, paid, employe, client_hotel, chambre
        --reservation information
        FROM reservation;
END;
/