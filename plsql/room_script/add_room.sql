CREATE OR REPLACE PROCEDURE add_room (
    p_id IN NUMBER, --the id of the room
    p_type IN VARCHAR2 DEFAULT NULL, -- 'simple', 'double', 'suite'
    p_etage IN NUMBER DEFAULT NULL, --the floor of the room
    p_nb_personnes IN NUMBER DEFAULT 0, --the maximum occupancy of the room
    p_prix IN FLOAT DEFAULT 0, --the price of a single night
    p_etat IN NUMBER DEFAULT 2 -- 0 for libre, 1 for occupée, 2 for maintenance
) AS
BEGIN
    --adding a room 
    INSERT INTO chambre (id_chambre, type_chambre, etage, nb_personnes, prix, etat)
    VALUES (p_id, p_type, p_etage, p_nb_personnes, p_prix, p_etat);
    COMMIT;
EXCEPTION
--when the room already exists in the database
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ID chambre déjà existant.');
    --handling other possible exceptions
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur inconnue : ' || SQLERRM);
END;
/