-- supprimer une réservation de la table reservation

CREATE OR REPLACE PROCEDURE delete_reservation(
    p_id IN NUMBER
) AS
BEGIN
    DELETE FROM reservation
    WHERE id_reservation = p_id;
    COMMIT;
END;
/