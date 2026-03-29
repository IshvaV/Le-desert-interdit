package Modele;

import java.util.ArrayList;
import java.util.Random;

public class Tempete {
    private final int[] cran  = {2,2,2,3,3,3,4,4,5,5,6,6,6};
    private double niveauindex;
    private int limitindex;
    // difficulty 0: Novice, 1: Normal, 2: Elite, 3: Légendaire
    public Tempete(int difficulty, int joueurnb){
        // Initialisation du niveau de la tempête en fonction de la difficulté
        int[] tempdif ={0,2,4,6};

        int difficultyindex = tempdif[difficulty];
        if (joueurnb == 2){
            this.limitindex = 12;
        } else if (joueurnb == 3 || joueurnb == 4){
            this.limitindex = 10;
        } else {
            this.limitindex = 8;
        }

        this.niveauindex = difficultyindex;
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
        if (cran[(int) (this.niveauindex + 1)] >= limitindex) {
            return false;
        }
        this.niveauindex ++;
        return true;
    }
}