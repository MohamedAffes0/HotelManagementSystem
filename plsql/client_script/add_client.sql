-- jouter un client à la table client_hotel

CREATE OR REPLACE PROCEDURE add_client_hotel (
    p_cin INT,
    p_nom VARCHAR2 DEFAULT NULL,
    p_prenom VARCHAR2 DEFAULT NULL,
    p_mail VARCHAR2 deFAULT NULL
) AS
BEGIN
    INSERT INTO client_hotel (cin, nom, prenom, mail)
    VALUES (p_cin, p_nom, p_prenom, p_mail);
    COMMIT;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : Le client avec le CIN ' || p_cin || ' existe déjà.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ' || SQLERRM);
END;
/