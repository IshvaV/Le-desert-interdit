package Modele;

public class Tunnel extends Tuile {

    public Tunnel(int x, int y, int nbSable) {
        super(x, y, nbSable);
    }

    @Override
    public void explore() {
        super.explored = true;
    }
}
