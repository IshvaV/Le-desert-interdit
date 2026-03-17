package Modele;


public abstract class Tuile {
    private int x,y;
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

    public void enleverSable() {
        if (this.nbSable > 0) this.nbSable--;
    }

    public boolean blocked() {
        return this.nbSable == 2;
    }

    public boolean isExplored() {
        return explored;
    }

    public abstract void explore();

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean hasPlayer(Joueur j) {
        return this == j;
    }
}