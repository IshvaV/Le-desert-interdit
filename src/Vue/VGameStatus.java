package Vue;

import Modele.Desert;
import Modele.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VGameStatus extends JPanel implements Observer {


    public VGameStatus(Desert desert) {
        JLabel playerStatus = new JLabel("Player Status : Joueur " + (desert.getJoueurs()[desert.getJoueurAct()].getId()+1));



        JLabel tempeteStatus = new JLabel("Tempête Niveau : " + desert.getTempete().getNiveau());
        this.add(playerStatus);
        this.add(tempeteStatus);

        desert.addObserver(this);


        //  JLabel JoueurRestant = new JLabel("Joueur Restant : " + desert.getNbJoueurs());

    }


    @Override
    public void update(Observable observable, Object arg) {
            Desert desert = (Desert) observable;
            this.removeAll();
            JLabel playerStatus = new JLabel("Player Status : Joueur " + (desert.getJoueurs()[desert.getJoueurAct()].getId()+1));
            JLabel tempeteStatus = new JLabel("Tempête Niveau : " + desert.getTempete().getNiveau());
            this.add(playerStatus);
            this.add(tempeteStatus);
            this.revalidate();
            this.repaint();
    }
}
