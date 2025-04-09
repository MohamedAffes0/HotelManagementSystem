CREATE OR REPLACE PROCEDURE add_room (
    p_id IN NUMBER,
    p_type IN VARCHAR2 DEFAULT NULL, -- 'simple', 'double', 'suite'
    p_etage IN NUMBER DEFAULT NULL,
    p_nb_personnes IN NUMBER DEFAULT 0,
    p_prix IN FLOAT DEFAULT 0,
    p_etat IN NUMBER DEFAULT 2 -- 0 for libre, 1 for occupée, 2 for maintenance
) AS
BEGIN
    INSERT INTO chambre (id_chambre, type_chambre, etage, nb_personnes, prix, etat)
    VALUES (p_id, p_type, p_etage, p_nb_personnes, p_prix, p_etat);
    COMMIT;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ID chambre déjà existant.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur inconnue : ' || SQLERRM);
END;
/