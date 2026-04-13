package Modele;

import java.util.*;

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
    private ModeAction modeActuel = ModeAction.DEPLACEMENT;
    private int reserveSable = 48;
    private Joueur cibleNavigation;


    public Desert(Difficulte difficulte, int nbJoueurs) {
        this.grille = new Grille();
        this.tempete = new Tempete(difficulte);
        this.nbJoueurs = nbJoueurs;
        this.joueurs = new Joueur[nbJoueurs];

        //Roles aléatoires
        ArrayList<Role> rolesDispo = new ArrayList<>(Arrays.asList(Role.values()));
        Collections.shuffle(rolesDispo);

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
            Role roleInit = rolesDispo.get(k);
            joueurs[k] = new Joueur(zc, k, roleInit);
            setChanged();
            notifyObservers("Joueur " + (k + 1) + " est : " + roleInit.getNom());
        }

        joueurAct = 0;
    }

    // Getters

    public void setModeActuel(ModeAction mode) {
        this.modeActuel = mode;
        setChanged();
        notifyObservers("Mode : " + mode.name());
    }

    public ModeAction getModeActuel() {
        return modeActuel;
    }

    // Méthodes

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
        int tirage = random.nextInt(100); // Tirage entre 0 et 99

        setChanged();

        if (tirage < 10) {
            // 10% : Tempête se déchaîne
            tempete.tempeteSeDechaine();
            notifyObservers("La tempête se déchaîne !");
        } else if (tirage < 30) {
            // 20% : Vague de chaleur (10 à 29)
            tempete.vagueChaleur(joueurs);
            notifyObservers("Une vague de chaleur frappe les joueurs !");
        } else {
            // 70% : Le vent souffle (30 à 99)
            tempete.ventSouffle(this);
            notifyObservers("Le vent souffle et déplace les tuiles !");
        }
        actualiserEtatJeu();
    }

    public void deplacerJoueur(Tuile tuile){
        if (partieTerminee) return;
        Joueur j = joueurs[joueurAct];

        if (j.getActionsDispo() <= 0) {
            setChanged();
            notifyObservers("Pas assez d'actions pour se déplacer");
            return;
        }

        if (tuile instanceof OeilTempete) {
            setChanged();
            notifyObservers("L'Oeil de la tempête est infranchissable !");
            return;
        }

        // Écarts de distance
        int dx = Math.abs(j.getPosition().getX() - tuile.getX());
        int dy = Math.abs(j.getPosition().getY() -tuile.getY());

        // Vérifications des distances
        boolean distanceOk = (dx + dy == 1);
        if (j.getRole() == Role.EXPLORATEUR && dx == 1 && dy == 1) {
            distanceOk = true;      //On autorise la diagonale pour l'explorateur
        }

        // Déplacement tunnels explorés
        boolean tunnelMove = (j.getPosition() instanceof Tunnel t1 && tuile instanceof Tunnel t2
                && t1.isExplored() && t2.isExplored());

        if (distanceOk || tunnelMove){
            // On vérifie le sable
            boolean estBloqueParSable = (j.getPosition().getNbSable() >= 2 || tuile.getNbSable() >= 2);

            if (j.getRole() == Role.GRIMPEUR || !estBloqueParSable) {
                j.setPosition(tuile);
                j.moinsUneAction();
                setChanged();
                notifyObservers("Joueur " + (j.getId() + 1) + " se déplace.");
            } else{
                setChanged();
                notifyObservers("Tuile bloquée par le sable !");
            }
        } else if (dx != 0 || dy != 0) {
            setChanged();
            notifyObservers("Trop loin pour se déplacer");
        }
        actualiserEtatJeu();
    }

    public void creuser(Tuile tuile) {
        if (partieTerminee) return;
        Joueur j = joueurs[joueurAct];

        // Écarts de distance
        int dx = Math.abs(j.getPosition().getX() - tuile.getX());
        int dy = Math.abs(j.getPosition().getY() -tuile.getY());

        // Distance autorisée pour creuser : 0 (soi-même), 1 (adjacente, ou diagonale pour l'explorateur)
        boolean peutCreuser = (dx + dy <= 1);
        if (j.getRole() == Role.EXPLORATEUR && dx == 1 && dy == 1) {
            peutCreuser = true;
        }

        if (j.getActionsDispo() > 0 && peutCreuser && tuile.getNbSable() > 0) {
            tuile.enleverSable();

            // Archéologue peut creuser de nouveau
            if (j.getRole() == Role.ARCHEOLOGUE && tuile.getNbSable() > 0){
                tuile.enleverSable();
                setChanged();
                notifyObservers("L'Archéologue creuse 2 tonnes de sables !");
            }else{
                setChanged();
                notifyObservers("Sable retiré !"); // Message pour le chat
            }
            j.moinsUneAction();
        } else if (j.getActionsDispo() > 0 && peutCreuser && tuile.getNbSable() == 0) {
            setChanged();
            notifyObservers("Pas de sable ici !");
        }
        actualiserEtatJeu();
    }

    public void explorer() {

        if (partieTerminee) return;
        Joueur j = joueurs[joueurAct];
        Tuile t = j.getPosition();

        if (j.getActionsDispo() <= 0) {
            setChanged();
            notifyObservers("Pas assez d'actions !");
            return;
        }

        if (t.getNbSable() > 0) {
            setChanged();
            notifyObservers("Retirez d'abord le sable pour explorer !");
            return;
        }

        if (t.isExplored()) {
            setChanged();
            notifyObservers("Déjà exploré");
            return;
        }

        //Action réussie
        t.explore();
        j.moinsUneAction();

        setChanged();
        if (t instanceof Oasis oasis) {
            if (!oasis.isEstMirage()) {
                // Règle : tous les joueurs sur la case boivent
                for (Joueur p : joueurs) {
                    if (p != null && p.getPosition() == t) p.remplirGourde(2);
                }
                notifyObservers("Oasis trouvée ! Tout le monde boit.");
            } else {
                notifyObservers("C'était un mirage...");
            }
        } else if (t instanceof Tunnel) {
            notifyObservers("Tunnel découvert !");
        } else if (t instanceof TuileIndice indice) {
            notifyObservers("Indice trouvé pour : " + indice.getTypePiece());
            verifierIntersection(indice.getTypePiece());
        } else {
            notifyObservers("Case explorée : " + t.getClass().getSimpleName());
        }
        actualiserEtatJeu();
    }

    public void verifierIntersection(TypePiece type) {
        TuileIndice indiceLigne = null;
        TuileIndice indiceColonne = null;

        for (int i = 0; i < Grille.X; i++) {
            for (int j = 0; j < Grille.Y; j++ ) {
                Tuile t = grille.getTuilesTableau()[i][j];
                if (t instanceof TuileIndice ti && ti.getTypePiece() == type) {
                    if (ti.isEstLigne()) indiceLigne = ti;
                    else indiceColonne = ti;
                }
            }
        }

        if (indiceLigne != null && indiceLigne.isExplored() && indiceColonne != null && indiceColonne.isExplored()) {
            int targetX = indiceColonne.getX();
            int targetY = indiceLigne.getY();

            Tuile cible = grille.getTuilesTableau()[targetX][targetY];

            if (cible.getPiece() == null) {
                cible.setPiece(new Piece(type));
                setChanged();
                notifyObservers("Pièce localisée : " + type + " est en (" + targetX + ", " + targetY + ") !");
            }
        }
    }

    public void ramasserPiece() {

        if (partieTerminee) return;
        Joueur j = joueurs[joueurAct];
        Tuile t = j.getPosition();

        // On vérifie si la tuile est explorée et contient une pièce disponible
        if (j.getActionsDispo() <= 0) {
            setChanged();
            notifyObservers("Pas assez d'actions pour ramasser");
            return;
        }
        if (!t.isExplored()) {
            setChanged();
            notifyObservers("Il faut d'abord explorer la case");
            return;
        }
        if (t.aUnePiece()) {
            t.prendrePiece(); // Enlève la pièce de la tuile
            this.nbPiecesTrouvees++;
            j.moinsUneAction();
            setChanged();
            notifyObservers("Pièce récupérée ! Plus que " + (4 - nbPiecesTrouvees) + ".");
        }else{
            setChanged();
            notifyObservers("Pas de pièce ici !");
        }
        actualiserEtatJeu();
    }

    public void donnerEau(Joueur cible) {
        if (partieTerminee) return;
        Joueur j = joueurs[joueurAct];

        if (j.getRole() != Role.PORTEUR_D_EAU) {
            setChanged();
            notifyObservers("Seul le porteur d'eau peut donner de l'eau !");
            return;
        }

        if (j.getGourde() <= 0) {
            setChanged();
            notifyObservers("Votre gourde est vide !");
            return;
        }

        if (cible == j){
            setChanged();
            notifyObservers("Vous ne pouvez pas donner d'eau à vous même !");
            return;
        }

        if (cible.getGourde() >= cible.getRole().getMaxGourde()) {
            setChanged();
            notifyObservers("La gourde de ce joueur est déjà pleine !");
            return;
        }

        // Le porteur d'eau peut donner en vertical, horizontal et en diagonale (distance 1)
        int dx = Math.abs(j.getPosition().getX() - cible.getPosition().getX());
        int dy = Math.abs(j.getPosition().getY() - cible.getPosition().getY());

        if (dx <= 1 && dy <= 1) {
            j.boire();
            cible.remplirGourde(1);
            setChanged();
            notifyObservers("Le Porteur d'eau a donné une dose à Joueur " + (cible.getId() + 1));
        } else {
            setChanged();
            notifyObservers("Ce joueur est trop loin !");
        }
        actualiserEtatJeu();
    }

    public void puiserEau() {
        if (partieTerminee) return;
        Joueur j = joueurs[joueurAct];

        if (j.getRole() != Role.PORTEUR_D_EAU) {
            setChanged();
            notifyObservers("Seul le Porteur d'eau peut puiser dans les oasis !");
            return;
        }

        if (j.getActionsDispo() <= 0) {
            setChanged();
            notifyObservers("Pas assez d'actions");
            return;
        }

        Tuile t = j.getPosition();
        if (t instanceof Oasis oasis && t.isExplored() && !oasis.isEstMirage()) {
            j.remplirGourde(2);
            j.moinsUneAction();
            setChanged();
            notifyObservers("Le Porteur d'eau a puisé 2 doses d'eau.");
        }else {
            setChanged();
            notifyObservers("Vous ne pouvez pas puiser ici");
        }
        actualiserEtatJeu();
    }

    public void selectionnerCible(Joueur cible) {
        if (joueurs[joueurAct].getRole() != Role.NAVIGATEUR) return;
        this.cibleNavigation = cible;
        this.modeActuel = ModeAction.NAVIGATE_DEST;
        setChanged();
        notifyObservers("Cliquez sur la destination !");
    }

    public void naviguer(Tuile dest) {
        Joueur j = joueurs[joueurAct];
        if (cibleNavigation != null && j.getActionsDispo() > 0) {
            int dx = Math.abs(cibleNavigation.getPosition().getX() - dest.getX());
            int dy = Math.abs(cibleNavigation.getPosition().getY() - dest.getY());

            if (dx + dy == 1) {
                cibleNavigation.setPosition(dest);
                j.moinsUneAction();
                this.modeActuel = ModeAction.DEPLACEMENT;
                setChanged();
                notifyObservers("Déplacement effectué.");
            } else {
                setChanged();
                notifyObservers("Trop loin !");
            }
        }
        actualiserEtatJeu();
    }

    public boolean poserSable(Tuile t) {
        if (reserveSable <= 0) {
            partieTerminee = true;
            setChanged();
            notifyObservers("DÉFAITE : plus de sable disponible");
            return false;
        }
        t.ajouterSable();
        reserveSable--;
        return true;
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
