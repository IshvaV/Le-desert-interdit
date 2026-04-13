package Modele;

import java.util.Random;

public class Tempete {

    private double niveau;
    private double limit;

    // difficulty 0: Novice, 1: Normal, 2: Elite, 3: Légendaire
    public Tempete(Difficulte difficulte){
        this.limit = 7.0;
        // Initialisation du niveau de la tempête en fonction de la difficulté
        switch (difficulte) {
            case NOVICE:
                this.niveau = 2.0;
                break;
            case NORMAL:
                this.niveau = 3.0;
                break;
            case ELITE:
                this.niveau = 4.0;
                break;
            case LEGENDAIRE:
                this.niveau = 5.0;
                break;
        }
    }

    public void ventSouffle(Desert desert) {
        Random random = new Random();
        int force = random.nextInt(3) + 1;
        Direction[] directions = Direction.values();
        int indexAleatoire = random.nextInt(directions.length);
        Direction directionAleatoire = directions[indexAleatoire];

        desert.getGrille().deplacerTuiles(directionAleatoire, force, desert);
    }

    public void vagueChaleur(Joueur[] joueurs) {
        for (Joueur joueur : joueurs) {
            if (joueur != null) {
                if (!(joueur.getPosition() instanceof Tunnel))
                    joueur.boire();
                if (joueur.estMort()) {
                    joueur.setPosition(null);
                }
            }
        }
    }

    public boolean tempeteSeDechaine() {
        if (niveau + 0.5 >= limit) {
            return false;
        }
        this.niveau+= 0.5;
        return true;
    }
    public double getNiveau() {
        return niveau;
    }
}