package Vue;

import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VGrille extends JPanel implements Observer {

    private Desert desert;
    private final static int TAILLE = 100;
    private Image imagebg;
    public VGrille(Desert desert) {
        this.desert = desert;
        this.desert.addObserver(this);

        java.net.URL imgURL = getClass().getResource("/data/Desert-image-2.png");
        if (imgURL != null) {
            this.imagebg = new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Image non trouvée : /data/Desert-image-2.png");
        }
        Dimension dim = new Dimension(Grille.X*TAILLE, Grille.Y*TAILLE);



    }




    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagebg != null) {
            g.drawImage(imagebg, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }



        for(int i =  0 ; i < Grille.X ; i++){
            for (int j =  0 ; j < Grille.Y ; j++){
                paint(g, desert.getGrille().getTuilesTableau()[i][j],(i)*TAILLE + getWidth()/3, (j)*TAILLE + getHeight()/5 );

            }
        }
    }
    public void paint(Graphics g, Tuile tuile, int i, int j) {
        if (tuile instanceof Oasis){
            g.setColor(Color.cyan);
        }else if(tuile instanceof ZoneCrash){
            
            g.setColor(Color.orange);
        } else if (tuile instanceof OeilTempete) {
            g.setColor(Color.red);
        }else {
            g.setColor(Color.yellow);
        }
        g.fillRect(i, j, TAILLE, TAILLE);
    }
}
