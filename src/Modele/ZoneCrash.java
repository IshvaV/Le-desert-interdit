package Modele;

public class ZoneCrash extends Tuile {

    public ZoneCrash(int x, int y, int nbSable) {
        super(x, y, nbSable);
    }

    @Override
    public void explore() {
        super.explored = true;
    }
}
