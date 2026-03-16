package Modele;


public abstract class Tuile {
    private int x,y;
    private int nbSable;
    private boolean explored;
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