CREATE OR REPLACE PROCEDURE requested_room(
    p_id OUT NUMBER, --the id of the room
    p_type OUT chambre.type_chambre%TYPE, --type of the room
    p_floor OUT chambre.etage%TYPE, --floor of the room
    p_capacity OUT chambre.nb_personnes%TYPE, --maximum occupancy of the room
    p_price OUT chambre.prix%TYPE, --price of a single night
    p_state OUT chambre.etat%TYPE --room status
) AS
BEGIN
    SELECT id_chambre, type_chambre, etage, nb_personnes, prix, etat
    --room information needed
    INTO p_id, p_type, p_floor, p_capacity, p_price, p_state
    FROM chambre 
    WHERE id_chambre = (
        -- Find the room ID with the highest number of reservations
        SELECT chambre 
        FROM reservation 
        GROUP BY chambre 
        ORDER BY COUNT(*) DESC
        FETCH FIRST 1 ROWS ONLY
    );

EXCEPTION
    --in case no data is found
    WHEN NO_DATA_FOUND THEN
        p_id := 0;
        p_type := NULL;
        p_floor := NULL;
        p_capacity := NULL;
        p_price := NULL;
        p_state := NULL;
    --handling other possible exceptions
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLCODE || ' - ' || SQLERRM);
        p_id := 0;
        p_type := NULL;
        p_floor := NULL;
        p_capacity := NULL;
        p_price := NULL;
        p_state := NULL;
END;
/