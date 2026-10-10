package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import domain.Client;
import domain.Commande;
import domain.Plat;
import service.ClientService;
import service.CommandeService;
import service.MenuService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class CommandeHttpServer {
    public static void main (String[] args) {
        int port = 5003;

        try {
            CommandeService commandeService = new CommandeService();

            // pré-remplir des commandes de test
            ClientService clientService = new ClientService();
            MenuService menuService = new MenuService();

            Client aminah = clientService.trouverClient(10);
            Plat burger = menuService.trouverPlat(2);
            commandeService.creerCommande(aminah,burger,13.50);

            HttpServer server = HttpServer.create(new InetSocketAddress(port),0);
            server.createContext("/commandes", exchange -> handleCommandes(exchange, commandeService));
            server.start();

            System.out.println("CommandeHttpServer demarre sur http://localhost:"+port);
            System.out.println("http://localhost:"+port+"/commandes");
            System.out.println("http://localhost:"+port+"/commandes/1");
            System.out.println("http://localhost:"+port+"/commandes/1/statut");
            System.out.println("http://localhost:"+port+"/commandes/2");

        } catch (IOException exception){
            System.err.println("Impossible de demarrer CommandeHttpServer: " + exception.getMessage());
        }
    }

    private static void handleCommandes(HttpExchange exchange, CommandeService commandeService) throws IOException {
        String path = exchange.getRequestURI().getPath();
        // GET /commandes -> lister toutes les commandes
        if(path.equals("/commandes")){
            send(exchange, formatCommandes(commandeService));
            return;
        }

        // GET /commandes/...
        if(path.startsWith("/commandes/")){
            String remainingPath = path.substring("/commandes/".length());
            String [] parts =  remainingPath.split("/");

            int commandeId = Integer.parseInt(parts[0]);

            // GET /commandes/1 -> une commande précise
            if(parts.length == 1){
                Commande commande = commandeService.trouverCommande(commandeId);
                if(commande == null){
                    send(exchange, "Commande introuvable !!");
                } else {
                    send(exchange, commande.toString());
                }
                return;
            }

            // GET /commandes/1/statut -> le statut d'une commande
            if(parts.length == 2 && parts[1].equals("statut")){
                Commande commande = commandeService.trouverCommande(commandeId);
                if(commande == null){
                    send(exchange, "Commande Introuvable !!");
                }else {
                    send(exchange, "Statut : "+commande.statut);
                }
                return;
            }
        }
        send(exchange, "Route inconnue !!");
    }

    private static String formatCommandes(CommandeService commandeService) {
        if(commandeService.getCommandes().isEmpty()){
            return "Aucune commande !!";
        }

        StringBuilder result = new StringBuilder();
        for(Commande commande : commandeService.getCommandes()){
            result.append(commande).append("\n");
        }
        return result.toString();
    }

    private static void send(HttpExchange exchange, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);

        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }

}
