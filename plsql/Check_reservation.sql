CREATE OR REPLACE PROCEDURE check_reservation (
    p_id_chambre IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT date_debut, date_fin
        FROM reservation
        WHERE chambre = p_id_chambre;
END;
/