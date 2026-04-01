package Vue;

import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VGrille extends JPanel implements Observer {

    private Desert desert;
    private final static int TAILLE = 50;
    public VGrille(Desert desert) {
        this.desert = desert;
        this.desert.addObserver(this);
        Dimension dim = new Dimension(Grille.X*TAILLE, Grille.Y*TAILLE);
        this.setPreferredSize(dim);
    }




    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i =  0 ; i < Grille.X ; i++){
            for (int j =  0 ; j < Grille.Y ; j++){
                paint(g, desert.getGrille().getTuilesTableau()[i][j],(i)*TAILLE, (j)*TAILLE );

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
