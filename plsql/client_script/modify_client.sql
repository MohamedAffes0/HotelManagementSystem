CREATE OR REPLACE PROCEDURE modify_client (
    p_cin IN NUMBER, --client id 
    p_nom IN STRING DEFAULT NULL, --client last name
    p_prenom IN STRING DEFAULT NULL, --client first name
    p_mail IN STRING DEFAULT NULL --client mail
) AS
BEGIN
    --modifying and updating the client table with the information given in the parameters
    UPDATE client_hotel 
    set nom = p_nom,
        prenom = p_prenom,
        mail = p_mail
    WHERE cin = p_cin;
    --in case no data is found
    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : Aucun client trouvé avec le CIN ' || p_cin);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Client ' || p_cin || ' modifié avec succès.');
    END IF;
END;
/