package Modele;

public class Desert {
    private Grille grille;
    private Tempete tempete;

    public Desert(int difficulty, int nbJoueurs) {
        this.grille = new Grille();
        this.tempete = new Tempete(difficulty, nbJoueurs);
    }

}
