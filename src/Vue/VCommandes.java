package Vue;

import Controleur.Controleur;
import Modele.Desert;

import javax.swing.*;
import java.awt.*;

public class VCommandes extends JPanel {

    private Desert desert;
    private JButton endTurnButton;
    private JButton deplaceButton;
    private JButton creuseButton;
    private JButton exploreButton;
    private JButton ramasseButton;
    private JButton donnerEau;
    private JButton puiserButton;
    private JButton navigateButton;

    VCommandes(Desert desert,Controleur controleur) {
        this.setBackground(new Color(244, 164, 96));
        this.setBorder(BorderFactory.createTitledBorder("Actions"));

        deplaceButton = addButton("Se déplacer", controleur);
        creuseButton = addButton("Creuser", controleur);
        exploreButton = addButton("Explorer", controleur);
        ramasseButton = addButton("Ramasser", controleur);
        donnerEau = addButton("Donner de l'eau", controleur);
        puiserButton = addButton("Puiser", controleur);
        navigateButton = addButton("Déplacer collègue", controleur);
        endTurnButton = addButton("Finir le tour", controleur);
    }
    private JButton addButton(String text, Controleur controleur) {
        JButton button = new JButton(text);
        button.addActionListener(controleur);
        this.add(button);
        return button;
    }
}
