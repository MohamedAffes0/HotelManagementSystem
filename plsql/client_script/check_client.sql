-- verifier si un client existe dans la table client_hotel

CREATE OR REPLACE PROCEDURE check_client (
    p_cin IN NUMBER,
    p_exists OUT NUMBER
) AS
BEGIN
    SELECT COUNT(*) INTO p_exists FROM client_hotel
    WHERE cin = p_cin;
    IF p_exists > 0 THEN
        p_exists := 1;
    ELSE
        p_exists := 0;
    END IF;
END;
/