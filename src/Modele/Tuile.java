package Modele;


public abstract class Tuile {
    private int x, y;
    private int nbSable;
    protected boolean explored;
    private Piece piece;

    public Tuile(int x, int y, int nbSable) {
        this.x = x;
        this.y = y;
        this.nbSable = nbSable;
        this.explored = false;
        this.piece = null;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getNbSable() {
        return nbSable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int setX(int x) {
        return this.x = x;
    }

    public int setY(int y) {
        return this.y = y;
    }

    public void enleverSable() {
        if (this.nbSable > 0) this.nbSable--;
    }

    public void ajouterSable() {
        if (nbSable < 5) this.nbSable++;
    }

    public boolean blocked() {
        return this.nbSable >= 2;
    }

    public boolean isExplored() {
        return explored;
    }

    public void explore() {
        explored = true;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

}