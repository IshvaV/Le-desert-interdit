package Vue;

import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class VGrille extends JPanel implements Observer, MouseListener {

    private Desert desert;
    private final static int TAILLE = 150;
    private Image imagebg;
    private Map<Class<?>, Image> tuileImages;


    public VGrille(Desert desert) {
        this.desert = desert;
        this.desert.addObserver(this);
        this.addMouseListener(this);

        java.net.URL imgURL = getClass().getResource("/data/Desert-image-2.png");
        if (imgURL != null) {
            this.imagebg = new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Image non trouvée : /data/Desert-image-2.png");
        }
        Dimension dim = new Dimension(Grille.X * TAILLE, Grille.Y * TAILLE);

        this.tuileImages = new HashMap<Class<?>, Image>();
        this.tuileImages.put(Oasis.class, new ImageIcon(getClass().getResource("/data/Desert-OS.png")).getImage());
        this.tuileImages.put(ZoneCrash.class, new ImageIcon(getClass().getResource("/data/Desert-ZC.png")).getImage());
        this.tuileImages.put(OeilTempete.class, new ImageIcon(getClass().getResource("/data/Desert-TP.png")).getImage());
        this.tuileImages.put(TuileOrdinaire.class, new ImageIcon(getClass().getResource("/data/Desert-NTH.png")).getImage());
        this.tuileImages.put(Tunnel.class, new ImageIcon(getClass().getResource("/data/Desert-TL.png")).getImage());
        this.tuileImages.put(TuileIndice.class, new ImageIcon(getClass().getResource("/data/Desert-NTH.png")).getImage());
        this.tuileImages.put(PisteDecollage.class, new ImageIcon(getClass().getResource("/data/Desert-NTH.png")).getImage());


    }


    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calcul des offsets pour centrer la grille dans l'espace disponible
        int offsetX = (getWidth() - Grille.X * TAILLE) / 2;
        int offsetY = (getHeight() - Grille.Y * TAILLE) / 2;
//image
        if (imagebg != null) {
            g.drawImage(imagebg, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

//tuiles
        for (int i = 0; i < Grille.X; i++) {
            for (int j = 0; j < Grille.Y; j++) {
                paintTuile(g, desert.getGrille().getTuilesTableau()[i][j], i * TAILLE + offsetX, j * TAILLE + offsetY);

            }
        }
//players !
        for (int i = 0; i < desert.getJoueurs().length; i++) {
            paintPlayers(g, desert.getJoueurs()[i], offsetX, offsetY);
        }

    }
    // On change les paramètres pour passer les offsets calculés
    public void paintPlayers(Graphics g, Joueur joueur, int offsetX, int offsetY) {
        if (joueur != null) {
            int id = joueur.getId(); //

            // Sélection de la couleur selon l'ID
            if(id == 0) g.setColor(Color.YELLOW);
            else if (id == 1) g.setColor(Color.RED);
            else if (id == 2) g.setColor(Color.BLUE);
            else g.setColor(Color.GREEN);

            // Calcul du décalage pour ne pas que les pions se cachent s'ils sont sur la même case
            int decalage = id * 10;

            // On utilise offsetX et offsetY
            int x = joueur.getPosition().getX() * TAILLE + offsetX + decalage;
            int y = joueur.getPosition().getY() * TAILLE + offsetY + decalage;

            g.fillRect(x, y, 20, 20);

            // Petit bonus : un contour noir pour mieux voir les pions sur le sable
            g.setColor(Color.BLACK);
            g.drawRect(x, y, 20, 20);
        }
    }

    public void paintTuile(Graphics g, Tuile tuile, int offsetX, int offsetY) {
        Image tuileImage = tuileImages.get(tuile.getClass());
        Image sable = new ImageIcon(getClass().getResource("/data/Desert_SD.png")).getImage();
        if (tuileImage != null) {
            g.drawImage(tuileImage, offsetX, offsetY, TAILLE, TAILLE, this);
            if (tuile.getNbSable() > 0) {
                g.drawImage(sable, offsetX, offsetY, TAILLE, TAILLE, this);
            }
        } else {
            System.out.println(tuile.getClass());
            g.setColor(Color.YELLOW);
            g.fillRect(offsetX, offsetY, TAILLE, TAILLE);
        }
    }

//    public void VExplore() {
//
//    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();


        int offsetX = (getWidth() - Grille.X * TAILLE) / 2;
        int offsetY = (getHeight() - Grille.Y * TAILLE) / 2;


        if (x >= offsetX && y >= offsetY) {
            // pixel en coord :p
            int i = (x - offsetX) / TAILLE;
            int j = (y - offsetY) / TAILLE;


            if (i >= 0 && i < Grille.X && j >= 0 && j < Grille.Y) {
                Tuile tuileCliquee = desert.getGrille().getTuilesTableau()[i][j];
                if (desert.getModeActuel() == ModeAction.DEPLACEMENT) {
                    desert.deplacerJoueur(tuileCliquee);
                } else if (desert.getModeActuel() == ModeAction.CREUSER) {
                    desert.creuser(tuileCliquee);
                }

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}