CREATE OR REPLACE PROCEDURE add_reservation (
    p_date_debut IN DATE, --when the stay starts
    p_date_fin IN DATE,--when it ends
    p_paid IN NUMBER DEFAULT 0, --to check if the client paid the reservation
    p_id_employe IN NUMBER, --the employe handling the reservation
    p_id_client IN NUMBER, --the id of the client 
    p_id_chambre IN NUMBER --the id of the room
) AS
BEGIN
    --adding a new reservation to the database
    INSERT INTO reservation (id_reservation, date_debut, date_fin, paid, employe, client_hotel, chambre)
    VALUES (reservation_id_seq.NEXTVAL, p_date_debut, p_date_fin, p_paid, p_id_employe, p_id_client, p_id_chambre);
    COMMIT;
EXCEPTION
    --when the reservation already exists in the database
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ID reservation déjà existant.');
    --in case of invalid parameters
    WHEN VALUE_ERROR THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : Valeur invalide pour l''un des paramètres.');
    --handling other possible exceptions
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur inconnue : ' || SQLERRM);
END;
/