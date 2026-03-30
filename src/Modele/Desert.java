package Modele;
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
        this.joueurs = new Joueur[nbJoueurs+1];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grille.getTuilesTableau()[i][j] instanceof ZoneCrash) {
                    zc = (ZoneCrash) grille.getTuilesTableau()[i][j];
                }
            }
        }
        for (int k = 0; k < nbJoueurs; k++){
            joueurs[k] = new Joueur(zc);


        }
        joueurAct = 1;
    }
    public void jouer(){

        joueurs[joueurAct].moinsUneAction();

        if (joueurs[joueurAct].estMort()){
           joueurs[joueurAct]=null;
        }

        if (joueurs[joueurAct].getActionsDispo() == 0){
            joueurs[joueurAct].actionsTermine();
            if (joueurAct == nbJoueurs){
                joueurAct = 1;
                tempete.tempeteSeDechaine();
            }else{
                if (partieFinit()){
                    while (joueurs[joueurAct] == null){
                        if (joueurAct == nbJoueurs){
                            joueurAct = 1;

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
}
