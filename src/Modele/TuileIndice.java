package Modele;

public class TuileIndice extends Tuile {

    public TuileIndice(int x, int y, int nbSable) {
        super(x, y, nbSable);
    }

    @Override
    public void explore() {
        super.explored = true;
    }

    public int getX(){
        return this.getX();
    }

    public int getY(){
        return this.getY();
    }
}
