CREATE OR REPLACE FUNCTION faithful_clients 
RETURN SYS_REFCURSOR
AS
    c_result SYS_REFCURSOR;
BEGIN
    OPEN c_result FOR
        SELECT c.cin, c.nom, c.prenom, c.mail
        FROM client_hotel c
        JOIN reservation r ON c.cin = r.client_hotel
        GROUP BY c.cin, c.nom, c.prenom, c.mail
        HAVING COUNT(*) >= (
            SELECT COUNT(*) * 0.1 FROM reservation
        );

    RETURN c_result;
END;
/