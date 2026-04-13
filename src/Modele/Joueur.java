package Modele;

import java.awt.*;
import java.util.ArrayList;

public class Joueur {

    private Tuile position;
    private Role role;
    private int gourde;
    private int actionsDispo;
    private ArrayList<Piece> inventaire;
    private int id;



    public Joueur(Tuile position, int id, Role role) {
        this.position = position;
        this.role = role;
        this.gourde = role.getMaxGourde();
        this.actionsDispo = 4;
        this.inventaire = new ArrayList<>();
        this.id = id;



    }

    // Getters

    public Tuile getPosition() {
        return position;
    }

    public Role getRole() { return role;}

    public int getGourde() {
        return gourde;
    }

    public int getActionsDispo() {
        return actionsDispo;
    }

    public ArrayList<Piece> getInventaire() {
        return inventaire;
    }

    public int getId() { return id;}


    // Méthodes utiles

    public void setPosition(Tuile position) { this.position = position; }

    public void ajouterPiece(Piece piece) {
        inventaire.add(piece);
    }

    public void moinsUneAction(){ actionsDispo--; }

    public void actionsTermine(){ actionsDispo = 4; }

    public void boire() { if (gourde > 0) gourde--; }

    public void remplirGourde(int portion) { gourde = Math.min(gourde + portion, role.getMaxGourde()); }

    //Vérifications

    public boolean aPiece(Piece piece) { return inventaire.contains(piece);}

    public boolean estMort(){ return gourde <= 0; }

}
