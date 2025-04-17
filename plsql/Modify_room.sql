CREATE OR REPLACE PROCEDURE modify_room (
    p_id IN NUMBER,--id of the room--
    p_nb_personnes IN NUMBER DEFAULT 0,--maximum occupancy of a room--
    p_prix IN FLOAT DEFAULT 0,--the price of a single night--
    p_etat IN NUMBER DEFAULT 2 -- 0 for libre, 1 for occupée, 2 for maintenance--
) AS
BEGIN
    --updating the price, state,maximum occupancy for the room with p_id--
    UPDATE chambre 
    set nb_personnes = p_nb_personnes,
        prix = p_prix,
        etat = p_etat
    WHERE id_chambre = p_id;
--if the room with p_id doesn't exist--
    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : Aucune chambre trouvée avec l''ID ' || p_id);
    ELSE
        --signaling the update--
        DBMS_OUTPUT.PUT_LINE('Chambre ' || p_id || ' modifiée avec succès.');
    END IF;
END;
/
