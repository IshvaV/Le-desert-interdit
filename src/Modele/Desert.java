package Modele;
import Vue.Fenetre;

import java.util.Observable;
import java.util.Observer;

public class Desert extends Observable {
    private Grille grille;
    private Tempete tempete;
    private int nbJoueurs;
    private Joueur[] joueurs;
    private int joueurAct;
    private ZoneCrash zc;


    public Desert(Difficulte difficulte, int nbJoueurs) {
        this.grille = new Grille();
        this.tempete = new Tempete(difficulte);
        this.nbJoueurs = nbJoueurs;
        this.joueurs = new Joueur[nbJoueurs];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grille.getTuilesTableau()[i][j] instanceof ZoneCrash) {
                    zc = (ZoneCrash) grille.getTuilesTableau()[i][j];
                }
            }
        }
        for (int k = 0; k < nbJoueurs; k++){
            joueurs[k] = new Joueur(zc, k);


        }
        joueurAct = 0;
    }
    public void jouer(){

        joueurs[joueurAct].moinsUneAction();

        if (joueurs[joueurAct].estMort()){
           joueurs[joueurAct]=null;
        }

        if (joueurs[joueurAct].getActionsDispo() == 0){
            joueurs[joueurAct].actionsTermine();
            if (joueurAct == nbJoueurs-1){
                joueurAct = 0;
                jouerActionsDuDesert();

            }else{
                if (partieFinit()){
                    while (joueurs[joueurAct] == null){
                        if (joueurAct == nbJoueurs-1){
                            joueurAct = 0;

                        }
                        joueurAct++;
                    }
                }


            }

        }

        notifyObservers();

    }

    public boolean partieFinit(){
        for (int i = 0; i < nbJoueurs; i++){
            if (joueurs[i] != null){
                return false;
            }

        }
        return true;
    }

    public void finDuTour() {
        Fenetre.vchat.addMessage("Joueur " + (joueurAct+1) + " a fini son tour");
        joueurAct++;
        if (joueurAct == nbJoueurs) joueurAct = 0;
        joueurs[joueurAct].actionsTermine();
        Fenetre.vchat.addMessage("Joueur " + (joueurAct+1) + " à ton tour");

        notifyObservers();

    }
    public void jouerActionsDuDesert(){
        tempete.tempeteSeDechaine();
        tempete.vagueChaleur(joueurs);
        tempete.ventSouffle(grille);
    }
    public void deplacerJoueur(Tuile tuile){
        if (tuile.getClass() != OeilTempete.class && joueurs[joueurAct].getActionsDispo() >0){

            int diag1 = Math.abs(tuile.getX() - joueurs[joueurAct].getPosition().getX());
            int diag2 = Math.abs(tuile.getY() - joueurs[joueurAct].getPosition().getY());
            if (diag1+diag2 ==1 && tuile.getNbSable() < 2){
                joueurs[joueurAct].setPosition(tuile);
                joueurs[joueurAct].moinsUneAction();
                notifyObservers();
            }
        }
    }

    public Grille getGrille() {
        return grille;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }
    public int getJoueurAct() {
        return joueurAct;
    }

    public Tempete getTempete() {
        return tempete;
    }
}
