CREATE OR REPLACE PROCEDURE delete_client(
    p_cin IN NUMBER --id of the client--
) AS
BEGIN
    --delete the client with p_cin from the database--
    DELETE FROM client_hotel 
    WHERE cin = p_cin;
    COMMIT;
END;
/
