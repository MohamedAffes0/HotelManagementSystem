package org.models;

public class RoomModel {
    private int id_chambre;
    private String type_chambre;
    private int etage;
    private int nb_personnes;
    private float prix;
    private int etat; //-- 0 for libre, 1 for occupée --, 2 for maintenance --

    public RoomModel(int id, String type, int etage, int nb_personne, float prix, int etat){
        this.id_chambre = id;
        this.type_chambre = type;
        this.etage = etage;
        this.nb_personnes = nb_personne;
        this.prix = prix;
        this.etat = etat;
    }

    public int getIdChambre() {
        return id_chambre;
    }

    public String getTypeChambre() {
        return type_chambre;
    }

    public int getEtage() {
        return etage;
    }

    public int getNbPersonnes() {
        return nb_personnes;
    }

    public float getPrix() {
        return prix;
    }

    public int getEtat() {
        return etat;
    }
    
    public void setEtat(int etat) {
        this.etat = etat;
    }
}
