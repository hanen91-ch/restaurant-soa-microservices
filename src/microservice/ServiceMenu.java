package microservice;

import domain.Plat;

import java.util.ArrayList;
import java.util.List;

public class ServiceMenu {

    List<Plat> plats;

    public ServiceMenu() {
        this.plats = new ArrayList<>();
        plats.add(new Plat(1, "Pizza Margherita", 12.00, true, 0.0));
        plats.add(new Plat(2, "Burger Maison", 15.00, true, 0.10));
        plats.add(new Plat(3, "Poutine Classique", 10.00, false, 0.0));
    }

    public List<Plat> getPlats() {
        return plats;
    }

    public Plat trouverPlat(int id) {
        for (Plat plat : plats) {
            if (plat.id == id) {
                return plat;
            }
        }
        return null;
    }

    public boolean platEstDisponible(int id) {
        Plat plat = trouverPlat(id);
        return plat != null && plat.disponible;
    }

    public double calculerPrixAvecPromotion(int id) {
        Plat plat = trouverPlat(id);
        if (plat == null) return -1;
        return plat.getPrixAvecPromotion();
    }
}
