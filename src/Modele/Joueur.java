package Modele;

import java.util.ArrayList;

public class Joueur {

    private Tuile position;
    private int gourde;
    private int actionsDispo;
    private ArrayList<Piece> inventaire;

    public Joueur(Tuile position, int gourde, int actionsDispo) {
        this.position = position;
        this.gourde = 5;
        this.actionsDispo = 4;
    }

    public void boire() {
        gourde++;
    }
}
