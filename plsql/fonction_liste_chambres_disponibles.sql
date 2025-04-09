CREATE OR REPLACE PACKAGE types_package AS
  TYPE chambre_rec IS RECORD (
    id_chambre chambre.id_chambre%type,
    etage chambre.etage%type,
    nb_personnes chambre.nb_personnes%type,
    prix_nuit chambre.prix_nuit%type,
    etat chambre.etat%type,
    type_chambre chambre.type_chambre%type
  );

  TYPE table_chambres IS TABLE OF chambre_rec INDEX BY Binary_INTEGER;
END;

CREATE OR REPLACE FUNCTION liste_chambres_disponibles(
    deb DATE,
    fin DATE,
    nb int
) RETURN types_package.table_chambres
IS
    liste types_package.table_chambres;
    i   Binary_INTEGER := 0;
BEGIN
    FOR ligne in (
        SELECT id_chambre, etage, nb_personnes, prix_nuit,etat,type_chambre
        FROM Chambre c
        WHERE etat='disponible' c.capacite >= p_nb_personnes
          AND c.etat = 'libre'
          AND id_chambre not in(
              SELECT id_chambre FROM réservation r
              where fin>r.date_debut and deb<r.date_fin
          )
    ) LOOP
        i:=i+1;
        liste(i).id_chambre:=ligne.id_chambre;
        liste(i).type_chambre:=ligne.type_chambre;
        liste(i).capacite:=ligne.capacite;
        liste(i).etat:= ligne.etat;
    END LOOP;

    return liste;
END;
