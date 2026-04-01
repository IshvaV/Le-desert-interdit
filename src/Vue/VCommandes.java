package Vue;

import Controleur.Controleur;
import Modele.Desert;

import javax.swing.*;

public class VCommandes extends JPanel {

    private Desert desert;

    VCommandes(Desert desert) {
        this.desert = desert;

        JButton button1 = new JButton("Start");
        this.add(button1);

        Controleur ctrl = new Controleur(desert);
        button1.addActionListener(ctrl);

    }
}
