CREATE OR REPLACE PROCEDURE get_all_rooms(
    p_cursor OUT SYS_REFCURSOR
    --oracle cursor that returns the rooms in a dynamic way
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT id_chambre, type_chambre, etage, nb_personnes, prix, etat
        --room information
        FROM chambre;
END;
/