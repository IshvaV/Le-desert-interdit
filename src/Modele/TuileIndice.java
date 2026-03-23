package Modele;

public class TuileIndice extends Tuile {

    private TypePiece typePiece;
    private boolean estLigne;

    public TuileIndice(int x, int y, int nbSable, TypePiece typePiece, boolean estLigne) {
        super(x, y, nbSable);
        this.typePiece = typePiece;
        this.estLigne = estLigne;
    }

    public TypePiece getTypePiece() {
        return typePiece;
    }

    public boolean isEstLigne() {
        return estLigne;
    }
}