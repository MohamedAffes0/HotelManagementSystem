CREATE OR REPLACE PROCEDURE add_reservation (
    p_date_debut IN DATE,
    p_date_fin IN DATE,
    p_paid IN NUMBER DEFAULT 0,
    p_id_employe IN NUMBER,
    p_id_client IN NUMBER,
    p_id_chambre IN NUMBER
) AS
BEGIN
    INSERT INTO reservation (id_reservation, date_debut, date_fin, paid, employe, client_hotel, chambre)
    VALUES (reservation_id_seq.NEXTVAL, p_date_debut, p_date_fin, p_paid, p_id_employe, p_id_client, p_id_chambre);
    COMMIT;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ID reservation déjà existant.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur inconnue : ' || SQLERRM);
END;
/