-- modifier une reservation

CREATE OR REPLACE PROCEDURE modify_reservation (
    p_id IN NUMBER,
    p_date_debut IN DATE,
    p_date_fin IN DATE,
    p_paid IN NUMBER DEFAULT 0 -- 0 payé, 1 non payé
) AS
BEGIN
    UPDATE reservation 
    set date_debut = p_date_debut,
        date_fin = p_date_fin,
        paid = p_paid
    WHERE id_reservation = p_id;
    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : Aucune reservation trouvée avec l''ID ' || p_id);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Reservation ' || p_id || ' modifiée avec succès.');
    END IF;
END;
/