-- ajouter une chambre

CREATE OR REPLACE PROCEDURE add_room (
    p_id IN NUMBER,
    p_type IN VARCHAR2 DEFAULT NULL, -- 'simple', 'double', 'suite'
    p_etage IN NUMBER DEFAULT NULL,
    p_nb_personnes IN NUMBER DEFAULT 0,
    p_prix IN FLOAT DEFAULT 0
) AS
BEGIN
    INSERT INTO chambre (id_chambre, type_chambre, etage, nb_personnes, prix)
    VALUES (p_id, p_type, p_etage, p_nb_personnes, p_prix);
    COMMIT;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ID chambre déjà existant.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur inconnue : ' || SQLERRM);
END;
/