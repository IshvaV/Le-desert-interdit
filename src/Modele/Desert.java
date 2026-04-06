package Modele;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Desert extends Observable {
    private Grille grille;
    private Tempete tempete;
    private int nbJoueurs;
    private Joueur[] joueurs;
    private int joueurAct;
    private ZoneCrash zc;
    private PisteDecollage pd;
    private boolean partieTerminee = false;
    private int nbPiecesTrouvees = 0; // Compteur de pièces


    public Desert(Difficulte difficulte, int nbJoueurs) {
        this.grille = new Grille();
        this.tempete = new Tempete(difficulte);
        this.nbJoueurs = nbJoueurs;
        this.joueurs = new Joueur[nbJoueurs];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grille.getTuilesTableau()[i][j] instanceof ZoneCrash) {
                    zc = (ZoneCrash) grille.getTuilesTableau()[i][j];
                }else if(grille.getTuilesTableau()[i][j] instanceof PisteDecollage){
                    pd = (PisteDecollage) grille.getTuilesTableau()[i][j];
                }
            }
        }
        for (int k = 0; k < nbJoueurs; k++){
            joueurs[k] = new Joueur(zc, k);


        }
        joueurAct = 0;
    }

    public void actualiserEtatJeu() {

        if (partieTerminee) return;

        // 1. Vérification immédiate de la défaite (Mort de soif)
        if (verifDefaite()) {
            partieTerminee = true;
            return;
        }

        // 2. Vérification de la victoire (Exemple : tous sur la piste avec les pièces)
        if (verifVictoire()) {
            partieTerminee = true;
            setChanged();
            notifyObservers("VICTOIRE : La machine est prête, décollage immédiat !");
            return;
        }

        // 3. Notification pour mettre à jour la vue (Scoreboard, etc.)
        setChanged();
        notifyObservers();
    }

    // Méthode utilitaire pour la victoire
    public boolean verifVictoire() {
        // 1. A-t-on les 4 pièces ?
        if (nbPiecesTrouvees  < 4) return false;

        // 2. Tout le monde est sur la piste ?
        for (Joueur j : joueurs) {
            if (j != null && j.getPosition() != pd) {
                return false;
            }
        }
        return true;    // Si on a les pièces et que tout le monde est sur la piste, c'est gagné !
    }

    private boolean verifDefaite() {
        for (Joueur j : joueurs) {
            if (j != null && j.getGourde() <= 0) {
                setChanged();
                notifyObservers("DÉFAITE : Le joueur " + (j.getId() + 1) + " est mort de soif !");
                return true;
            }
        }
        return false;
    }

    public void finDuTour() {
        // 1. Notifier la fin du tour du joueur actuel pour le chat
        setChanged();
        notifyObservers("Joueur " + (joueurAct + 1) + " a fini son tour");

        // 2. Le désert joue ses actions
        for (int i = 0; i < (int) tempete.getNiveau(); i++) {
            jouerActionsDuDesert();
        }

        // 2. On vérifie si la partie est perdue pour tout le monde
        if (verifDefaite()) return;

        // 3. SEULEMENT APRÈS, on change de joueur

        joueurAct = (joueurAct + 1) % nbJoueurs;
        joueurs[joueurAct].actionsTermine();

        setChanged();
        notifyObservers("Joueur " + (joueurAct + 1) + " à ton tour");

        // 4. Pour séparer les tours

        setChanged();
        notifyObservers("-----------------------------");

        setChanged();
        notifyObservers();
    }

    public void jouerActionsDuDesert() {
        Random random = new Random();
        ActionDesert[] actions = ActionDesert.values();
        int indexAleatoire = random.nextInt(actions.length);
        ActionDesert act = actions[indexAleatoire];

        //On prépare le changement pour le message du chat
        setChanged();

        switch (act) {
            case TEMPETE_DECHAINE:
                tempete.tempeteSeDechaine();
                notifyObservers("La tempête de déchaîne !");
                break;
            case VAGUE_CHALEUR:
                tempete.vagueChaleur(joueurs);
                notifyObservers("Une vague de chaleur frappe les joueurs !");
                break;
            case VENT_SOUFFLE:
                tempete.ventSouffle(grille);
                notifyObservers("Le vent souffle et déplace les tuiles !");
                break;
        }
        // Cette méthode vérifie la mort ET rafraîchit toute la vue (notify)
        actualiserEtatJeu();
    }

    public void deplacerJoueur(Tuile tuile){
        if (partieTerminee) return;
        Joueur j = joueurs[joueurAct];
        // On ne peut pas aller sur l'oeil, et il faut des actions
        if (!(tuile instanceof OeilTempete) && j.getNbActions() > 0){

            // Distance = 1 pour se déplacer (adjacentes)
            int dist = Math.abs(tuile.getX() - j.getPosition().getX()) +
                    Math.abs(tuile.getY() - j.getPosition().getY());

            // Déplacement normal (distance 1) ou entre Tunnels explorés
            boolean tunnelMove = (j.getPosition() instanceof Tunnel t1 && tuile instanceof Tunnel t2
                    && t1.isExplored() && t2.isExplored());

            if ((dist == 1 || tunnelMove) && tuile.getNbSable() < 2){
                j.setPosition(tuile);
                j.moinsUneAction();
            }
        }
        actualiserEtatJeu();
    }

    public void creuser(Tuile tuile) {
        if (partieTerminee) return;
        Joueur j = joueurs[joueurAct];
        int dist = Math.abs(tuile.getX() - j.getPosition().getX()) +
                Math.abs(tuile.getY() - j.getPosition().getY());

        if (j.getNbActions() > 0 && (dist == 0 || dist == 1) && tuile.getNbSable() > 0) {
            tuile.enleverSable();
            j.moinsUneAction();

            setChanged();
            notifyObservers("Sable retiré !"); // Message pour le chat
        }
        actualiserEtatJeu();
    }

    public void explorer() {

        if (partieTerminee) return;
        Joueur j = joueurs[joueurAct];
        Tuile t = j.getPosition();

        if (j.getNbActions() > 0 && t.getNbSable() == 0 && !t.isExplored()) {
            t.explore();
            j.moinsUneAction();

            setChanged();
            if (t instanceof Oasis oasis) {
                if (!oasis.isEstMirage()) {
                    // Règle : tous les joueurs sur la case boivent
                    for (Joueur p : joueurs) {
                        if (p != null && p.getPosition() == t) p.remplirGourde();
                    }
                    setChanged();
                    notifyObservers("Oasis trouvée ! Tout le monde boit.");
                } else {
                    setChanged();
                    notifyObservers("C'était un mirage...");
                }
            } else {
                setChanged();
                notifyObservers("Case explorée : " + t.getClass().getSimpleName());
            }
        }
        actualiserEtatJeu();
    }

    public void ramasserPiece() {

        if (partieTerminee) return;
        Joueur j = joueurs[joueurAct];
        Tuile t = j.getPosition();

        // On vérifie si la tuile est explorée et contient une pièce disponible
        if (j.getNbActions() > 0 && t.isExplored() && t.aUnePiece()) {
            t.prendrePiece(); // Enlève la pièce de la tuile
            this.nbPiecesTrouvees++;
            j.moinsUneAction();

            setChanged();
            notifyObservers("Pièce récupérée ! Plus que " + (4 - nbPiecesTrouvees) + ".");
            actualiserEtatJeu();
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
