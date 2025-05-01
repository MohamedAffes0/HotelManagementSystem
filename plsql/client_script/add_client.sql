CREATE OR REPLACE PROCEDURE add_client_hotel (
    p_cin INT, --the id of the client
    p_nom VARCHAR2 DEFAULT NULL, --the name of the client
    p_prenom VARCHAR2 DEFAULT NULL,--the last name of the client
    p_mail VARCHAR2 deFAULT NULL--the mail of the client
) AS
BEGIN
    INSERT INTO client_hotel (cin, nom, prenom, mail)
    VALUES (p_cin, p_nom, p_prenom, p_mail);
    COMMIT;
EXCEPTION
    --when the client exists already in the database
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : Le client avec le CIN ' || p_cin || ' existe déjà.');
    --handling other possible exceptions
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ' || SQLERRM);
END;
/