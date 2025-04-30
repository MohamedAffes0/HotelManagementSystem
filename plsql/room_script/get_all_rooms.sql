CREATE OR REPLACE PROCEDURE get_all_rooms(
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT id_chambre, type_chambre, etage, nb_personnes, prix
        FROM chambre;
END;
/