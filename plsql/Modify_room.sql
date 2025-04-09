CREATE OR REPLACE PROCEDURE modify_room (
    p_id IN NUMBER,
    p_nb_personnes IN NUMBER DEFAULT 0,
    p_prix IN FLOAT DEFAULT 0,
    p_etat IN NUMBER DEFAULT 2 -- 0 for libre, 1 for occupée, 2 for maintenance
) AS
BEGIN
    UPDATE chambre 
    set nb_personnes = p_nb_personnes,
        prix = p_prix,
        etat = p_etat
    WHERE id_chambre = p_id;
    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : Aucune chambre trouvée avec l''ID ' || p_id);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Chambre ' || p_id || ' modifiée avec succès.');
    END IF;
END;
/