package domain;

public class Client {
    public final int id;
    public final String nom;
    public final boolean actif;
    public final boolean vip;

    public Client(int id, String nom, boolean actif, boolean vip) {
        this.id = id;
        this.nom = nom;
        this.actif = actif;
        this.vip = vip;
    }

    @Override
    public String toString() {
        String statut = actif ? "actif" : "inactif";
        String type = vip ? "VIP" : "normal";
        return id + " - " + nom + " (" + statut + " - " + type + ")";
    }
}
