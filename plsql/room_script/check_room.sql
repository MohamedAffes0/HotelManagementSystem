CREATE OR REPLACE PROCEDURE check_room (
    p_id IN NUMBER,
    p_exists OUT NUMBER
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