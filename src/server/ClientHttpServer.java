package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import domain.Client;
import microservice.ServiceClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ClientHttpServer {
    public static void main(String[] args) {
        int port = 5001;

        try {
            ServiceClient serviceClient = new ServiceClient();
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            server.createContext("/clients", exchange -> handleClients(exchange, serviceClient));
            server.start();

            System.out.println("ClientHttpServer demarre sur http://localhost:" + port);
            System.out.println("http://localhost:" + port + "/clients/10");
            System.out.println("http://localhost:" + port + "/clients/10/actif");
            System.out.println("http://localhost:" + port + "/clients/10/frais-service");

        } catch (IOException exception) {
            System.err.println("Impossible de demarrer ClientHttpServer: " + exception.getMessage());
        }
    }

    private static void handleClients(HttpExchange exchange, ServiceClient serviceClient) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.startsWith("/clients/")) {
            String remainingPath = path.substring("/clients/".length());
            String[] parts = remainingPath.split("/");

            int clientId = Integer.parseInt(parts[0]);

            // GET /clients/{id}
            if (parts.length == 1) {
                Client client = serviceClient.trouverClient(clientId);
                if (client == null) {
                    send(exchange, "Client introuvable.");
                } else {
                    send(exchange, client.toString());
                }
                return;
            }

            // GET /clients/{id}/actif
            if (parts.length == 2 && parts[1].equals("actif")) {
                boolean actif = serviceClient.clientEstActif(clientId);
                if (actif) {
                    send(exchange, "Le client est actif.");
                } else {
                    send(exchange, "Le client est inactif.");
                }
                return;
            }

            // GET /clients/{id}/frais-service
            if (parts.length == 2 && parts[1].equals("frais-service")) {
                double frais = serviceClient.calculerFraisService(clientId);
                send(exchange, "Frais de service: " + frais + " $");
                return;
            }
        }

        send(exchange, "Route inconnue.");
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
