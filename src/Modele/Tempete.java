package Modele;

import java.util.ArrayList;
import java.util.Random;

public class Tempete {
    private double niveau;

    public Tempete(){
        this.niveau = 2;
    }

    public void ventSouffle(Grille g) {
        Random random = new Random();
        int force = random.nextInt(3) + 1;
        Direction[] directions = Direction.values();
        int indexAleatoire = random.nextInt(directions.length);
        Direction directionAleatoire = directions[indexAleatoire];

        g.deplacerTuiles(directionAleatoire, force);
    }

    public void vagueChaleur(ArrayList<Joueur> joueurs) {
        for (Joueur joueur : joueurs) {
            if (!(joueur.getPosition() instanceof Tunnel))
                joueur.boire();
        }
    }

    public boolean tempeteSeDechaine() {
        if ((this.niveau + 0.5) >= 7) {
            return false;
        }
        niveau+=0.5;
        return true;
    }
}