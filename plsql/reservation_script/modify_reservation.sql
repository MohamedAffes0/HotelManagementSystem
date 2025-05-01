CREATE OR REPLACE PROCEDURE modify_reservation (
    p_id IN NUMBER, --the id of the reservation
    p_date_debut IN DATE, --when the stay starts
    p_date_fin IN DATE, --when it ends
    p_paid IN NUMBER DEFAULT 0 -- 0 for unpaid, 1 for paid
) AS
BEGIN
    --modifying and updating the reservation table with the information given in the parameters
    UPDATE reservation 
    set date_debut = p_date_debut,
        date_fin = p_date_fin,
        paid = p_paid
    WHERE id_reservation = p_id;
    --if the reservation doesn't exist in the database
    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : Aucune reservation trouvée avec l''ID ' || p_id);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Reservation ' || p_id || ' modifiée avec succès.');
    END IF;
END;
/