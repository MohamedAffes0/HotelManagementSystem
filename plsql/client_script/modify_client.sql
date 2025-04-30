-- modifier un client

CREATE OR REPLACE PROCEDURE modify_client (
    p_cin IN NUMBER,
    p_nom IN STRING DEFAULT NULL,
    p_prenom IN STRING DEFAULT NULL,
    p_mail IN STRING DEFAULT NULL
) AS
BEGIN
    UPDATE client_hotel 
    set nom = p_nom,
        prenom = p_prenom,
        mail = p_mail
    WHERE cin = p_cin;
    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : Aucun client trouvé avec le CIN ' || p_cin);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Client ' || p_cin || ' modifié avec succès.');
    END IF;
END;
/