CREATE OR REPLACE PROCEDURE get_all_clients(
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT cin, nom, prenom, mail
        FROM client_hotel;
END;
/