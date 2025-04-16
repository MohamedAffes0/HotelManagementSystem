CREATE OR REPLACE PROCEDURE requested_room(
    p_id OUT NUMBER,
    p_type OUT chambre.type_chambre%TYPE,
    p_floor OUT chambre.etage%TYPE,
    p_capacity OUT chambre.nb_personnes%TYPE,
    p_price OUT chambre.prix%TYPE,
    p_state OUT chambre.etat%TYPE
) AS
BEGIN
    SELECT id_chambre, type_chambre, etage, nb_personnes, prix, etat
    INTO p_id, p_type, p_floor, p_capacity, p_price, p_state
    FROM chambre 
    WHERE id_chambre = (
        SELECT chambre 
        FROM reservation 
        GROUP BY chambre 
        ORDER BY COUNT(*) DESC
        FETCH FIRST 1 ROWS ONLY
    );

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        p_id := 0;
        p_type := NULL;
        p_floor := NULL;
        p_capacity := NULL;
        p_price := NULL;
        p_state := NULL;
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