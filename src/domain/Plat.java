package domain;

public class Plat {
    public final int id;
    public final String nom;
    public final double prix;
    public final boolean disponible;
    public final double promotion;


    public Plat(int id, String nom, double prix, boolean disponible, double promotion) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.disponible = disponible;
        this.promotion = promotion;
    }

    public double getPrixAvecPromotion() {
        return prix * (1 - promotion);
    }

    @Override
    public String toString() {
        String dispo = disponible ? "disponible" : "non disponible";
        return id + " - " + nom + " - " + prix + " $ (" + dispo + ")";
    }
}
