CREATE OR REPLACE PROCEDURE delete_client(
    p_cin IN NUMBER --te id of the client that we want to delete from the database
) AS
BEGIN
    --deleting the client
    DELETE FROM client_hotel 
    WHERE cin = p_cin;
    COMMIT;
END;
/