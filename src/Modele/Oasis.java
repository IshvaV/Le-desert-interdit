package Modele;

public class Oasis extends Tuile{

    public Oasis(int x, int y, int nbSable) {
        super(x, y, nbSable);
    }

    public void explore() {
        super.explored = true;
    }
}
