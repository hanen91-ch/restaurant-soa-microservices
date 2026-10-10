package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import domain.Plat;
import microservice.ServiceMenu;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class MenuHttpServer {
    public static void main(String[] args) {
        int port = 5002;

        try {
            ServiceMenu serviceMenu = new ServiceMenu();
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            server.createContext("/plats", exchange -> handlePlats(exchange, serviceMenu));
            server.start();

            System.out.println("MenuHttpServer demarre sur http://localhost:" + port);
            System.out.println("http://localhost:" + port + "/plats");
            System.out.println("http://localhost:" + port + "/plats/1");
            System.out.println("http://localhost:" + port + "/plats/1/disponible");
            System.out.println("http://localhost:" + port + "/plats/1/prix-final");

        } catch (IOException exception) {
            System.err.println("Impossible de demarrer MenuHttpServer: " + exception.getMessage());
        }
    }

    private static void handlePlats(HttpExchange exchange, ServiceMenu serviceMenu) throws IOException {
        String path = exchange.getRequestURI().getPath();

        // GET /plats
        if (path.equals("/plats")) {
            StringBuilder sb = new StringBuilder();
            sb.append("-------- Menu Disponible --------\n");
            for (Plat plat : serviceMenu.getPlats()) {
                if (plat.disponible) {
                    sb.append("- ").append(plat.nom)
                            .append(" : ").append(plat.getPrixAvecPromotion())
                            .append(" $\n");
                }
            }
            send(exchange, sb.toString());
            return;
        }

        if (path.startsWith("/plats/")) {
            String remainingPath = path.substring("/plats/".length());
            String[] parts = remainingPath.split("/");

            int platId = Integer.parseInt(parts[0]);

            // GET /plats/{id}
            if (parts.length == 1) {
                Plat plat = serviceMenu.trouverPlat(platId);
                if (plat == null) {
                    send(exchange, "Plat introuvable.");
                } else {
                    send(exchange, plat.toString());
                }
                return;
            }

            // GET /plats/{id}/disponible
            if (parts.length == 2 && parts[1].equals("disponible")) {
                boolean dispo = serviceMenu.platEstDisponible(platId);
                if (dispo) {
                    send(exchange, "Le plat est disponible.");
                } else {
                    send(exchange, "Le plat n'est pas disponible.");
                }
                return;
            }

            // GET /plats/{id}/prix-final
            if (parts.length == 2 && parts[1].equals("prix-final")) {
                double prix = serviceMenu.calculerPrixAvecPromotion(platId);
                if (prix < 0) {
                    send(exchange, "Plat introuvable.");
                } else {
                    send(exchange, "Prix final: " + prix + " $");
                }
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
