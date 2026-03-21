package Modele;

public class TuileIndice extends Tuile {

    private Piece typePiece;
    private boolean estLigne;

    public TuileIndice(int x, int y, int nbSable, Piece typePiece, boolean estLigne) {
        super(x, y, nbSable);
        this.typePiece = typePiece;
        this.estLigne = estLigne;
    }

    public Piece getTypePiece() {
        return typePiece;
    }

    public boolean isEstLigne() {
        return estLigne;
    }
}