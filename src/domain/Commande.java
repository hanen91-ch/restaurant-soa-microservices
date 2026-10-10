package domain;

public class Commande {
    public final int id;
    public final Client client;
    public final Plat plat;
    public final double total;
    public StatutCommande statut;


    public Commande(int id, Client client, Plat plat, double total) {
        this.id = id;
        this.client = client;
        this.plat = plat;
        this.total = total;
        this.statut = StatutCommande.CREEE;
    }

    @Override
    public String toString() {
        return "Commande " + id + ": " + client.nom + " - " + plat.nom
                + " - " + total + " $ - " + statut;
    }
}
