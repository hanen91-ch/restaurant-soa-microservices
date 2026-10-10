package service;

import domain.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private final List<Client> clients = new ArrayList<>();

    public ClientService() {
        clients.add(new Client(10, "Aminah", true, true)); // VIP actif
        clients.add(new Client(20, "Devi", false, false)); // inactif
        clients.add(new Client(30, "Sarah", true, false)); // normal actif
    }

    public Client trouverClient(int clientId) {
        System.out.println("ClientService : recherche du client" + clientId);
        for (Client client : clients) {
            if(client.id == clientId){
                return client;
            }
        }
        return null;
    }

    public boolean clientEstActif(int clientId) {
        Client client = trouverClient(clientId);
        return client != null && client.actif;
    }

    public boolean clientEstVIP(int clientId) {
        Client client = trouverClient(clientId);
        return client != null && client.vip;
    }

    public double calculerFraisService(int clientId) {
        System.out.println("ClientService : calcul des frais pour client" + clientId);
        Client client = trouverClient(clientId);
        if (client == null || !client.actif) {
            return -1; // client invalide
        }
        return client.vip ? 0.00 : 2.00;
    }

}
