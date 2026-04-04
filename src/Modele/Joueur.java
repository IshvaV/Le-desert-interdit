package Modele;

import java.util.ArrayList;

public class Joueur {

    private Tuile position;
    private int gourde;
    private int actionsDispo;
    private ArrayList<Piece> inventaire;
    private int id;


    public Joueur(Tuile position, int id) {
        this.position = position;
        this.gourde = 5;
        this.actionsDispo = 4;
        this.inventaire = new ArrayList<>();
        this.id = id;
    }

    public void boire() {
        gourde--;
    }

    public boolean estMort(){
        return gourde <= 0;
    }

    public Tuile getPosition() {
        return position;
    }

    public int getGourde() {
        return gourde;
    }

    public int getActionsDispo() {
        return actionsDispo;
    }

    public ArrayList<Piece> getInventaire() {
        return inventaire;
    }

    public void setPosition(Tuile position) {
        this.position = position;
    }

    public void moinsUneAction(){
        actionsDispo--;
    }

    public void actionsTermine(){
        actionsDispo = 4;
    }

    public void remplirGourde() {
        gourde = Math.min(gourde + 2, 5);
    }

    public void ajouterPiece(Piece piece) {
        inventaire.add(piece);
    }

    public boolean aPiece(Piece piece) {
        return inventaire.contains(piece);
    }

    public int getId() {
        return id;
    }

    public int getNbActions(){
        return actionsDispo;
    }

}
