CREATE OR REPLACE PROCEDURE check_room (
    p_id IN NUMBER, --the id of the room
    p_exists OUT NUMBER --1 if the room exists else 0
) AS
BEGIN
    BEGIN
        SELECT COUNT(*) INTO p_exists
        FROM chambre
        WHERE id_chambre = p_id;

        IF p_exists > 0 THEN
            p_exists := 1;
        ELSE
            p_exists := 0;
        END IF;
    END;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_exists := 0;
        WHEN OTHERS THEN
            p_exists := 0;
END;
/