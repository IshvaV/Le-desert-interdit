package Vue;

import Modele.Desert;
import Modele.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VGameStatus extends JPanel implements Observer {

    private JLabel playerStatus;
    private JLabel actionStatus;
    private JLabel tempeteStatus;

    public VGameStatus(Desert desert) {
        this.setBackground(new Color(244, 164, 96));
        this.setBorder(BorderFactory.createTitledBorder("Statut"));
        
        Joueur j = desert.getJoueurs()[desert.getJoueurAct()];
        playerStatus = new JLabel("Joueur " + (j.getId() + 1) + " (" + j.getRole().getNom() + ")");
        actionStatus = new JLabel(" | Actions : " + j.getActionsDispo());
        tempeteStatus = new JLabel(" | Tempête : " + desert.getTempete().getNiveau());

        this.add(playerStatus);
        this.add(actionStatus);
        this.add(tempeteStatus);

        desert.addObserver(this);
    }


    @Override
    public void update(Observable observable, Object arg) {
            Desert desert = (Desert) observable;
            Joueur j = desert.getJoueurs()[desert.getJoueurAct()];
            playerStatus.setText("Joueur " + (j.getId() + 1) + " (" + j.getRole().getNom() + ")");
            actionStatus.setText(" | Actions : " + j.getActionsDispo());
            tempeteStatus.setText(" | Tempête : " + desert.getTempete().getNiveau());
    }
}
