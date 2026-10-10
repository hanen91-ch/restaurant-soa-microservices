package microservice;

import domain.Client;

import java.util.ArrayList;
import java.util.List;

public class ServiceClient {

    List<Client> clients;

    public ServiceClient(){
        this.clients = new ArrayList<>();
        clients.add(new Client(10, "Aminah", true, true)); // VIP actif
        clients.add(new Client(20, "Devi", false, false)); // inactif normal
        clients.add(new Client(30, "Sarah", true, false)); // actif normal
    }

    public List<Client> getClients(){
        return clients;
    }

    public Client trouverClient(int clientId){
        for (Client client : clients){
            if (client.id == clientId){
                return client;
            }
        }
        return null;
    }

    public boolean clientEstActif(int clientId){

        Client client = trouverClient(clientId);
        return client != null && client.actif;
    }

    public double calculerFraisService(int clientId){
        Client client = trouverClient(clientId);
        if (client == null || !client.actif) return -1;
        return client.vip ? 0.00 : 2.00;
    }
}

