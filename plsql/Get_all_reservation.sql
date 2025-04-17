CREATE OR REPLACE PROCEDURE get_all_reservation(
    p_cursor OUT SYS_REFCURSOR --cursor that will dynamically return the data regarding the reservations--
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT id_reservation, date_debut, date_fin, paid, employe, client_hotel, chambre
        FROM reservation;
END;
/
