CREATE OR REPLACE PROCEDURE add_reservation (
    p_date_debut IN DATE, --start date of the reservation--
    p_date_fin IN DATE, --end date of the reservation--
    p_paid IN NUMBER DEFAULT 0, --payment--
    p_id_employe IN NUMBER, --id of the employe who created the reservation--
    p_id_client IN NUMBER, --id of the client who did the reservation--
    p_id_chambre IN NUMBER --id of the chosen room--
) AS
BEGIN
    INSERT INTO reservation (id_reservation, date_debut, date_fin, paid, employe, client_hotel, chambre)
    VALUES (reservation_id_seq.NEXTVAL, p_date_debut, p_date_fin, p_paid, p_id_employe, p_id_client, p_id_chambre);
--insert a new reservation in the database--
    COMMIT;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ID reservation déjà existant.');--when the reservation exists already in the database--
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur inconnue : ' || SQLERRM);--handle other exceptions--
END;
/
