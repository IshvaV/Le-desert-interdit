package Vue;

import Controleur.Controleur;
import Modele.Desert;

import javax.swing.*;

public class VCommandes extends JPanel {

    private Desert desert;
    private JButton endTurnButton;
    VCommandes(Desert desert,Controleur controleur) {


        endTurnButton = new JButton("Finir le tour");
        this.add(endTurnButton); //t
        endTurnButton.addActionListener(controleur);
    }
}
