-- retourner l'état d'une chambre


CREATE OR REPLACE FUNCTION get_room_state(
    p_id_chambre IN reservation.chambre%TYPE
) RETURN INTEGER AS
    v_client_hotel reservation.client_hotel%TYPE;
    v_nb_client INTEGER;
    v_etat INTEGER; -- 0 libre, 1 occupée, 2 maintenance
BEGIN
    SELECT client_hotel, COUNT(*) INTO v_client_hotel, v_nb_client FROM reservation
    where chambre = p_id_chambre AND TRUNC(SYSDATE) BETWEEN TRUNC(date_debut) AND TRUNC(date_fin)
    GROUP BY client_hotel;

    -- si le client est null alors la chambre est reservée pour maintenance
    IF v_nb_client = 0 THEN
        v_etat := 0; -- libre
    ELSE
        IF v_client_hotel IS NULL THEN
            v_etat := 2; -- maintenance
        ELSE
            v_etat := 1; -- ocupée
        END IF;
    END IF;
    return v_etat;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No rooms found.');
        v_etat := 0; -- libre
        RETURN v_etat;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM(SQLCODE));
        v_etat := 0; -- libre
        RETURN v_etat;
END;
/