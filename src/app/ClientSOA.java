package app;

import bus.BusService;
import service.ClientService;
import service.CommandeService;
import service.MenuService;

public class ClientSOA {
    public static void main(String[] args) {

        // le clientSOA ne connait que le BusService
        ClientService clientService = new ClientService();
        MenuService menuService = new MenuService();
        CommandeService commandeService = new CommandeService();
        BusService busService = new BusService(clientService, menuService, commandeService);

        // Commande valide : Aminah (VIP) commande Burger Maison (promotion 10%)
        System.out.println("--------- Test1 : Commande valide ----------");
        String resultat1 = busService.creerCommandeComplete(10, 2);
        System.out.println(resultat1);
        System.out.println();

        // client inactif : Devi ne peut pas commander
        System.out.println("--- Test 2 : Client inactif ---");
        String resultat2 = busService.creerCommandeComplete(20, 1);
        System.out.println(resultat2);
        System.out.println();

        // plat non disponible : poutine classique
        System.out.println("--- Test 3 : Plat non disponible ---");
        String resultat3 = busService.creerCommandeComplete(30, 3);
        System.out.println(resultat3);
        System.out.println();

        // Annulation de la commande 1
        System.out.println("--- Test 4 : Annulation de la commande 1 ---");
        String resultat4 = busService.annulerCommandeComplete(1);
        System.out.println(resultat4);
        System.out.println();

        // Tentative d'annuler une commande deja annulee
        System.out.println("--- Test 5 : Commande deja annulee ---");
        String resultat5 = busService.annulerCommandeComplete(1);
        System.out.println(resultat5);
        System.out.println();

    }
}
