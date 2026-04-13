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
    private Map<TypePiece, Image> indiceImages;
    private Map<TypePiece, Image> pieceImages;
    private Image imgSable;
    private Image imgDos;


    public VGrille(Desert desert) {
        this.desert = desert;
        this.desert.addObserver(this);
        this.addMouseListener(this);

        java.net.URL imgURL = getClass().getResource("/data/Desert-image-2.png");
        if (imgURL != null) {
            this.imagebg = new ImageIcon(imgURL).getImage();
        }
        
        this.imgSable = new ImageIcon(getClass().getResource("/data/Desert_SD.png")).getImage();
        this.imgDos = new ImageIcon(getClass().getResource("/data/Desert-NTH.png")).getImage();

        this.tuileImages = new HashMap<>();
        this.tuileImages.put(Oasis.class, new ImageIcon(getClass().getResource("/data/Desert-OS.png")).getImage());
        this.tuileImages.put(ZoneCrash.class, new ImageIcon(getClass().getResource("/data/Desert-ZC.png")).getImage());
        this.tuileImages.put(OeilTempete.class, new ImageIcon(getClass().getResource("/data/Desert-TP.png")).getImage());
        this.tuileImages.put(TuileOrdinaire.class, new ImageIcon(getClass().getResource("/data/Desert-NTH.png")).getImage());
        this.tuileImages.put(Tunnel.class, new ImageIcon(getClass().getResource("/data/Desert-TL.png")).getImage());
        this.tuileImages.put(PisteDecollage.class, new ImageIcon(getClass().getResource("/data/Desert-ZD.png")).getImage());

        // Chargement des indices et pièces
        this.indiceImages = new HashMap<>();
        this.pieceImages = new HashMap<>();
        
        mapResource(TypePiece.MOTEUR, "Moteur");
        mapResource(TypePiece.HELICE, "Helice");
        mapResource(TypePiece.GOUVERNAIL, "Gouvernail");
        mapResource(TypePiece.CAPTEUR_SOLAIRE, "Capteur");
    }

    private void mapResource(TypePiece type, String suffix) {
        indiceImages.put(type, new ImageIcon(getClass().getResource("/data/Indice_" + suffix + ".png")).getImage());
        pieceImages.put(type, new ImageIcon(getClass().getResource("/data/Piece_" + suffix + ".png")).getImage());
    }


    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int offsetX = (getWidth() - Grille.X * TAILLE) / 2;
        int offsetY = (getHeight() - Grille.Y * TAILLE) / 2;

        if (imagebg != null) {
            g.drawImage(imagebg, 0, 0, getWidth(), getHeight(), this);
        }

        for (int i = 0; i < Grille.X; i++) {
            for (int j = 0; j < Grille.Y; j++) {
                paintTuile(g, desert.getGrille().getTuilesTableau()[i][j], i * TAILLE + offsetX, j * TAILLE + offsetY);
            }
        }

        for (int i = 0; i < desert.getJoueurs().length; i++) {
            paintPlayers(g, desert.getJoueurs()[i], offsetX, offsetY);
        }
    }

    public void paintPlayers(Graphics g, Joueur joueur, int offsetX, int offsetY) {
        if (joueur != null && joueur.getPosition() != null) {
            int id = joueur.getId();
            if(id == 0) g.setColor(Color.YELLOW);
            else if (id == 1) g.setColor(Color.RED);
            else if (id == 2) g.setColor(Color.BLUE);
            else g.setColor(Color.GREEN);

            int x = joueur.getPosition().getX() * TAILLE + offsetX + (TAILLE/2) - 10 + (id * 4);
            int y = joueur.getPosition().getY() * TAILLE + offsetY + (TAILLE/2) - 10 + (id * 4);

            g.fillOval(x, y, 20, 20);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, 20, 20);
        }
    }

    public void paintTuile(Graphics g, Tuile tuile, int x, int y) {
        Image imgAMontrer = imgDos;

        // Les tuiles révélées d'office ou après exploration
        boolean estRevelee = tuile.isExplored() ||
                            tuile instanceof Tunnel ||
                            tuile instanceof Oasis ||
                            tuile instanceof ZoneCrash ||
                            tuile instanceof OeilTempete;

        if (estRevelee) {
            if (tuile instanceof TuileIndice ti) {
                imgAMontrer = indiceImages.get(ti.getTypePiece());
            } else {
                imgAMontrer = tuileImages.get(tuile.getClass());
            }
        }

        if (imgAMontrer != null) {
            g.drawImage(imgAMontrer, x, y, TAILLE, TAILLE, this);
        }

        // Si une pièce est présente sur la tuile et révélée
        if (tuile.getPiece() != null && estRevelee) {
            Image pImg = pieceImages.get(tuile.getPiece().getNom());
            if (pImg != null) {
                g.drawImage(pImg, x + TAILLE/4, y + TAILLE/4, TAILLE/2, TAILLE/2, this);
            }
        }

        // Grille discrète
        g.setColor(new Color(0, 0, 0, 50));
        g.drawRect(x, y, TAILLE, TAILLE);

        // Sable empilé vers l'intérieur
        for (int k = 0; k < tuile.getNbSable(); k++) {
            int sOff = k * 4;
            g.drawImage(imgSable, x + sOff + 10, y + sOff + 10, TAILLE - 40, TAILLE - 40, this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int offsetX = (getWidth() - Grille.X * TAILLE) / 2;
        int offsetY = (getHeight() - Grille.Y * TAILLE) / 2;

        if (x >= offsetX && y >= offsetY) {
            int i = (x - offsetX) / TAILLE;
            int j = (y - offsetY) / TAILLE;

            if (i >= 0 && i < Grille.X && j >= 0 && j < Grille.Y) {
                Tuile tuileCliquee = desert.getGrille().getTuilesTableau()[i][j];
                if (desert.getModeActuel() == ModeAction.DEPLACEMENT) {
                    desert.deplacerJoueur(tuileCliquee);
                } else if (desert.getModeActuel() == ModeAction.CREUSER) {
                    desert.creuser(tuileCliquee);
                } else if (desert.getModeActuel() == ModeAction.DONNER_EAU) {
                    for (Joueur p : desert.getJoueurs()) {
                        if (p != null && p.getPosition() == tuileCliquee) {
                            desert.donnerEau(p);
                            break;
                        }
                    }
                } else if (desert.getModeActuel() == ModeAction.NAVIGATE_CIBLE) {
                    for (Joueur p : desert.getJoueurs()) {
                        if (p != null && p.getPosition() == tuileCliquee) {
                            desert.selectionnerCible(p);
                            break;
                        }
                    }
                } else if (desert.getModeActuel() == ModeAction.NAVIGATE_DEST) {
                    desert.naviguer(tuileCliquee);
                }
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
