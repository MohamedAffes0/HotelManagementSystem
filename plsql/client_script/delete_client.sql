-- supprimer un client de la table client_hotel

CREATE OR REPLACE PROCEDURE delete_client(
    p_cin IN NUMBER
) AS
BEGIN
    DELETE FROM client_hotel 
    WHERE cin = p_cin;
    COMMIT;
END;
/