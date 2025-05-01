CREATE OR REPLACE PROCEDURE modify_room (
    p_id IN NUMBER, --the id of the room
    p_nb_personnes IN NUMBER DEFAULT 0, --the maximum occupancy of the room
    p_prix IN FLOAT DEFAULT 0,--the price of a single night
    p_etat IN NUMBER DEFAULT 2 -- 0 for libre, 1 for occupée, 2 for maintenance
) AS
BEGIN
    --modifying and updating the room table with the information given in the parameters
    UPDATE chambre 
    set nb_personnes = p_nb_personnes,
        prix = p_prix,
        etat = p_etat
    WHERE id_chambre = p_id;
    --if the room doesn't exist in the database
    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : Aucune chambre trouvée avec l''ID ' || p_id);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Chambre ' || p_id || ' modifiée avec succès.');
    END IF;
END;
/