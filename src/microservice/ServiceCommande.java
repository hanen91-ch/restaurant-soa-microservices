package microservice;

import domain.Commande;
import domain.StatutCommande;

import java.util.ArrayList;
import java.util.List;

public class ServiceCommande {
    List<Commande> commandes;
    private int prochainId = 1;

    public ServiceCommande() {
        this.commandes = new ArrayList<>();
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public Commande trouverCommande(int id) {
        for (Commande commande : commandes) {
            if (commande.id == id) {
                return commande;
            }
        }
        return null;
    }

    public boolean commandePeutEtreAnnulee(int id) {
        Commande commande = trouverCommande(id);
        return commande != null && commande.statut == StatutCommande.CREEE;
    }

    public Commande annulerCommande(int id) {
        Commande commande = trouverCommande(id);
        if (commande == null) return null;
        commande.statut = StatutCommande.ANNULEE;
        return commande;
    }
}
