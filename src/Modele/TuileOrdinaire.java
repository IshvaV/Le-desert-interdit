package Modele;

public class TuileOrdinaire extends Tuile{

    public TuileOrdinaire(int x, int y, int nbSable) {
        super(x, y, nbSable);
    }

    @Override
    public void explore() {
        super.explored = true;
    }
}
