package Vue;

import Modele.Desert;
import Modele.Joueur;

import javax.swing.*;
import java.awt.*;

public class VGameStatus extends JPanel {


    public VGameStatus(Desert desert) {
        JLabel playerStatus = new JLabel("Player Status : Joueur " + (desert.getJoueurs()[desert.getJoueurAct()].getId()+1));



        JLabel tempeteStatus = new JLabel("Tempête Niveau : " + desert.getTempete().getNiveau());
        this.add(playerStatus);
        this.add(tempeteStatus);


        //  JLabel JoueurRestant = new JLabel("Joueur Restant : " + desert.getNbJoueurs());

    }


}
