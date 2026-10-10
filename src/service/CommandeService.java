package service;

import domain.Client;
import domain.Commande;
import domain.Plat;
import domain.StatutCommande;

import java.util.ArrayList;
import java.util.List;

public class CommandeService {

    private final List<Commande> commandes = new ArrayList<>();
    private int prochainId = 1;

    public Commande creerCommande(Client client, Plat plat, double total) {
        System.out.println("CommandeService : creer de la commande ");
        Commande commande = new Commande(prochainId, client, plat, total);
        prochainId++;
        commandes.add(commande);
        return commande;
    }

    public Commande trouverCommande(int commandeId) {
        System.out.println("CommandeService : trouver de la commande " +  commandeId);
        for (Commande commande : commandes) {
            if(commande.id == commandeId) {
                return commande;
            }
        }
        return null;
    }

    public boolean commandePeutEtreAnnulee(int commandeId) {
        Commande commande = trouverCommande(commandeId);
        return commande != null && commande.statut == StatutCommande.CREEE;
    }

    public Commande annulerCommande(int commandeId) {
        System.out.println("CommandeService : annuler de la commande " +  commandeId);
        Commande commande = trouverCommande(commandeId);
        if (commande == null) return null;
        commande.statut = StatutCommande.ANNULEE;
        return commande;
    }

    public void listerCommandes() {
        if (commandes.isEmpty()) {
            System.out.println("Aucune commande !");
            return;
        }
        for (Commande commande : commandes) {
            System.out.println(commande);
        }
    }

    public List<Commande> getCommandes(){
        return commandes;
    }

}
