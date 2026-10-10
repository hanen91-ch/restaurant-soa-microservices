package service;

import domain.Plat;

import java.util.ArrayList;
import java.util.List;

public class MenuService {

    private final List<Plat> plats = new ArrayList<>();

    public MenuService() {
        plats.add(new Plat(1, "Pizza Margherita" , 12.00, true, 0.0));
        plats.add(new Plat(2, "Burger Maison", 15.00, true, 0.10));
        plats.add(new Plat(3, "Poutine Classique", 10.00, false, 0.0));
    }

    public Plat trouverPlat(int platId){
        System.out.println("MenuService: recherche du plat " + platId);
        for(Plat plat : plats) {
            if (plat.id == platId){
                return plat;
            }
        }
        return null;
    }

    public boolean platEstDisponible(int platId){
        Plat plat = trouverPlat(platId);
        return plat != null && plat.disponible;
    }

    public double calculerPrixAvecPromotion(int platId){
        System.out.println("MenuService: calcul du prix avec promotion pour plat " + platId);
        Plat plat = trouverPlat(platId);
        if(plat == null) return -1;
        return plat.getPrixAvecPromotion();
    }

    public void afficherMenuDisponible(){
        System.out.println("-------- Menu Disponible --------");
        for(Plat plat : plats){
            if(plat.disponible){
                System.out.println("- " + plat.nom + " : " + plat.getPrixAvecPromotion() + " $");
            }
        }
    }








}
