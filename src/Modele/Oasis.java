package Modele;

public class Oasis extends Tuile{

    private boolean estMirage;

    public Oasis(int x, int y, int nbSable, boolean estMirage) {
        super(x, y, nbSable);
        this.estMirage = estMirage;
    }

    public boolean isEstMirage() {
        return estMirage;
    }

}
