CREATE OR REPLACE PROCEDURE check_client (
    p_cin IN NUMBER,--the id of the client--
    p_exists OUT NUMBER--equals 1 if the client exits, else 0--
) AS
BEGIN
    --check if the client exists in the database--
    SELECT COUNT(*) INTO p_exists FROM client_hotel
    WHERE cin = p_cin;
    IF p_exists > 0 THEN
        p_exists := 1;
    ELSE
        p_exists := 0;
    END IF;
END;
/
