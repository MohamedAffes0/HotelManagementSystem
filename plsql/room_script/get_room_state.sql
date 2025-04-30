CREATE OR REPLACE PROCEDURE get_room_state(
    p_id_chambre IN chambre.id_chambre%TYPE,
    p_etat OUT NUMBER -- 0 for all rooms, 1 for available rooms, 2 for reserved rooms
) AS
    v_client_hotel reservation.client_hotel%TYPE;
    v_nb_client INTEGER;
BEGIN
    SELECT client_hotel, COUNT(*) INTO v_client_hotel, v_nb_client FROM reservation
    where chambre = p_id_chambre AND SYSDATE BETWEEN date_debut AND date_fin;

    IF v_nb_client = 0 THEN
        p_etat := 0; -- available
    ELSE
        IF v_client_hotel IS NULL THEN
            p_etat := 2; -- reserved
        ELSE
            p_etat := 1; -- occupied
        END IF;
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No rooms found.');
        p_etat := 0; -- available
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM(SQLCODE));
        p_etat := 0; -- available
END;
/