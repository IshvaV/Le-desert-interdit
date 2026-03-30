package Modele;

import java.util.ArrayList;
import java.util.Random;

public class Tempete {

    private double niveau;
    private int limit;
    // difficulty 0: Novice, 1: Normal, 2: Elite, 3: Légendaire
    public Tempete(int difficulty, int joueurnb){
        // Initialisation du niveau de la tempête en fonction de la difficulté
        int[] tempdif ={0,2,4,6};

        int difficultyindex = tempdif[difficulty];
        if (joueurnb == 2){
            this.limit = 12;
        } else if (joueurnb == 3 || joueurnb == 4){
            this.limit = 10;
        } else {
            this.limit = 8;
        }

        this.niveau = difficultyindex;
    }

    public void ventSouffle(Grille g) {
        Random random = new Random();
        int force = random.nextInt(3) + 1;
        Direction[] directions = Direction.values();
        int indexAleatoire = random.nextInt(directions.length);
        Direction directionAleatoire = directions[indexAleatoire];

        g.deplacerTuiles(directionAleatoire, force);
    }

    public void vagueChaleur(Joueur[] joueurs) {
        for (Joueur joueur : joueurs) {
            if (!(joueur.getPosition() instanceof Tunnel))
                joueur.boire();
        }
    }

    public boolean tempeteSeDechaine() {
        if (niveau + 0.5 >= limit) {
            return false;
        }
        this.niveau++;
        return true;
    }
}