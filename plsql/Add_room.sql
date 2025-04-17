CREATE OR REPLACE PROCEDURE add_room (
    p_id IN NUMBER, --id of the room--
    p_type IN VARCHAR2 DEFAULT NULL, -- type of the room : it can be 'simple', 'double', 'suite'--
    p_etage IN NUMBER DEFAULT NULL,--floor of the room--
    p_nb_personnes IN NUMBER DEFAULT 0,--Maximum Occupancy per Room--
    p_prix IN FLOAT DEFAULT 0,--the price of a single night--
    p_etat IN NUMBER DEFAULT 2 -- 0 for libre, 1 for occupée, 2 for maintenance--
) AS
BEGIN
    --insert the data regarding a room in the database--
    INSERT INTO chambre (id_chambre, type_chambre, etage, nb_personnes, prix, etat)
    VALUES (p_id, p_type, p_etage, p_nb_personnes, p_prix, p_etat);
    COMMIT;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ID chambre déjà existant.');--detect the presence of a room in the database--
--handle
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur inconnue : ' || SQLERRM);--handle other possible exceptions--
END;
/
