CREATE OR REPLACE PROCEDURE add_client_hotel (
    p_cin INT, --client id--
    p_nom VARCHAR2 DEFAULT NULL, --name of the client--
    p_prenom VARCHAR2 DEFAULT NULL,--last name of the client--
    p_mail VARCHAR2 deFAULT NULL --mail of the client--
) AS
BEGIN
    INSERT INTO client_hotel (cin, nom, prenom, mail)
    VALUES (p_cin, p_nom, p_prenom, p_mail); --insert the data regarding the new client in the database--
    COMMIT;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : Le client avec le CIN ' || p_cin || ' existe déjà.'); --detect the presence of the client in the database--
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ' || SQLERRM); --handle other possible exceptions--
END;
/
