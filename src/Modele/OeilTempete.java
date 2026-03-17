package Modele;

public class OeilTempete extends Tuile {

    public OeilTempete(int x, int y) {
        super(x,y,0);
    }

    @Override
    public void explore() {
        super.explored = true;
    }
}
