CREATE OR REPLACE PROCEDURE get_all_clients(
    p_cursor OUT SYS_REFCURSOR
    --oracle cursor that returns the clients in a dynamic way
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT cin, nom, prenom, mail --client information
        FROM client_hotel;
END;
/
