-- verifie si la chambre est déjà réservée

CREATE OR REPLACE PROCEDURE check_reservation (
    p_id_chambre IN NUMBER,
    p_id_reservation_acutel IN NUMBER, -- id de la reservation actuelle pour ignorer la reservation en cours en cas de modification
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT date_debut, date_fin
        FROM reservation
        WHERE chambre = p_id_chambre AND id_reservation != p_id_reservation_acutel;
END;
/