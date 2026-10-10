package bus;

import domain.Client;
import domain.Commande;
import domain.Plat;
import service.ClientService;
import service.CommandeService;
import service.MenuService;

public class BusService {

    private final ClientService clientService;
    private final MenuService menuService;
    private final CommandeService commandeService;

    public BusService(ClientService clientService, MenuService menuService, CommandeService commandeService) {
        this.clientService = clientService;
        this.menuService = menuService;
        this.commandeService = commandeService;
    }

    public String creerCommandeComplete(int clientId, int platId){
        System.out.println("BusService: demande de creation de commande recu");

        // verifier que le client est existe
        Client client = clientService.trouverClient(clientId);
        if (client == null){
            return "Commande refusee: Client n'existe pas";
        }

        // verifier que le client est actif
        if (!clientService.clientEstActif(clientId)){
            return "Commande refusee: le Client" + client.nom + "est inactif !";
        }

        // verifier que le plat est existe
        Plat plat = menuService.trouverPlat(platId);
        if (plat == null){
            return "Commande refusee: Plat n'existe pas";
        }

        // verifier le plat est disponible
        if (!menuService.platEstDisponible(platId)){
            return "Commande refusee: le Plat" + plat.nom + "n'est pas  disponible !";
        }

        // calculer le prix du plat avec promotion
        double prixAvecPromotion = menuService.calculerPrixAvecPromotion(platId);

        // calculer les frais de service du client
        double fraisService = clientService.calculerFraisService(clientId);

        // Calculer le total final
        double total = prixAvecPromotion + fraisService;

        // creer la commande
        Commande commande = commandeService.creerCommande(client, plat, total);

        // retourner une confirmation
        return "Commande creee:\n"
                + "Client: " + client.nom + "\n"
                + "Plat: " + plat.nom + "\n"
                + "Prix avec promotion: " + prixAvecPromotion + " $\n"
                + "Frais de service: " + fraisService + " $\n"
                + "Total: " + total + " $\n"
                + "Statut: " + commande.statut;
    }

    public String annulerCommandeComplete(int commandeId){
        System.out.println("BusService: demande d'annulation commande recu");

        // verifier que la commande existe
        if (commandeService.trouverCommande(commandeId) == null){
            return "Commande introuvable !";
        }

        // verifier que la commande peut etre annulee
        if (!commandeService.commandePeutEtreAnnulee(commandeId)){
            return "Commande impossible a annuler";
        }

        // annuler la commande
        commandeService.annulerCommande(commandeId);
        return "Commande " + commandeId + " annulee avec succes ";
    }








}
