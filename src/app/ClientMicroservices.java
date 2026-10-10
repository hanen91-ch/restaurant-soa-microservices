package app;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientMicroservices {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        System.out.println("=== Partie microservices avec HTTP ===");
        System.out.println();

        // Menu (port 5002)
        appelerService(client, "http://localhost:5002/plats");
        appelerService(client, "http://localhost:5002/plats/1");
        appelerService(client, "http://localhost:5002/plats/1/disponible");
        appelerService(client, "http://localhost:5002/plats/1/prix-final");

        // Clients (port 5001)
        appelerService(client, "http://localhost:5001/clients/10");
        appelerService(client, "http://localhost:5001/clients/10/actif");
        appelerService(client, "http://localhost:5001/clients/10/frais-service");

        // Commandes (port 5003)
        appelerService(client, "http://localhost:5003/commandes");
        appelerService(client, "http://localhost:5003/commandes/1");
        appelerService(client, "http://localhost:5003/commandes/1/statut");
    }

    private static void appelerService(HttpClient client, String adresse)
            throws IOException, InterruptedException {

        System.out.println("GET " + adresse);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(adresse))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        System.out.println(response.body());
        System.out.println();
    }
}