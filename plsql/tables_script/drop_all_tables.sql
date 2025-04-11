-- Suppression de toutes les contraintes de clés primaires et étrangères dans la base de données Oracle

BEGIN
    FOR r IN (SELECT constraint_name, table_name, constraint_type 
        FROM user_constraints 
        WHERE constraint_type IN ('P', 'R')) -- 'P' pour clés primaires, 'R' pour clés étrangères
    LOOP
        -- Vérifier si la contrainte est une clé étrangère ('R') ou une clé primaire ('P')
        BEGIN
            IF r.constraint_type = 'R' THEN
                -- Supprimer les clés étrangères
                EXECUTE IMMEDIATE 'ALTER TABLE ' || r.table_name || ' DROP CONSTRAINT ' || r.constraint_name;
            ELSIF r.constraint_type = 'P' THEN
                -- Supprimer les clés primaires
                EXECUTE IMMEDIATE 'ALTER TABLE ' || r.table_name || ' DROP CONSTRAINT ' || r.constraint_name;
            END IF;
        EXCEPTION
            WHEN OTHERS THEN
                -- Ignorer l'erreur si la contrainte n'existe pas ou une autre erreur survient
                NULL;
        END;
    END LOOP;
END;
/

-- Cette procédure supprime toutes les séquences de l'utilisateur actuel dans Oracle.

BEGIN
    FOR r IN (SELECT sequence_name FROM user_sequences) LOOP
        -- Vérifier si la séquence existe avant de la supprimer
        BEGIN
            EXECUTE IMMEDIATE 'DROP SEQUENCE ' || r.sequence_name;
        EXCEPTION
            WHEN OTHERS THEN
                -- Si la séquence n'existe pas ou une autre erreur se produit, ignorer l'erreur
                NULL;
        END;
    END LOOP;
END;
/
-- Cette procédure PL/SQL supprime toutes les tables de l'utilisateur actuel dans la base de données Oracle.

BEGIN
    FOR r IN (SELECT table_name FROM user_tables) LOOP
        -- Vérifier si la table existe avant de la supprimer
        BEGIN
            EXECUTE IMMEDIATE 'DROP TABLE ' || r.table_name || ' CASCADE CONSTRAINTS';
        EXCEPTION
            WHEN OTHERS THEN
                -- Si la table n'existe pas ou une autre erreur se produit, ignorer l'erreur
                NULL;
        END;
    END LOOP;
END;
/