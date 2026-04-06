package Vue;

import Controleur.Controleur;
import Modele.Desert;
import Modele.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VScoreBoardSingle extends JPanel implements Observer {

    private JLabel title;
    private Joueur joueur;


    private JLabel actionLabel;
    private JLabel gourdeLabel;

    //private JButton endTurnButton;
    public VScoreBoardSingle(Desert desert, Joueur joueur) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));

        desert.addObserver(this);


        JLabel titre = new JLabel("Joueur " + (joueur.getId() + 1));
        titre.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));

        this.joueur = joueur;

        // Petit indicateur coloré
        JPanel colorIcon = new JPanel();
        Color[] colors = {Color.YELLOW, Color.RED, Color.BLUE, Color.GREEN};
        colorIcon.setBackground(colors[joueur.getId()]);
        colorIcon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        colorIcon.setPreferredSize(new Dimension(20, 20));


        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        titleBar.add(colorIcon);
        titleBar.add(titre);
        titleBar.setAlignmentY(Component.TOP_ALIGNMENT);


        this.actionLabel = new JLabel("Actions restantes : " + joueur.getNbActions());
        this.gourdeLabel = new JLabel("Gourdes restantes : " + joueur.getGourde());


        this.add(titleBar);



        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(this.actionLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(this.gourdeLabel);
        infoPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        // Assembler
        this.add(infoPanel);






//
//        endTurnButton = new JButton("Finir le tour");
//        this.add(endTurnButton); //t
//        endTurnButton.addActionListener(controleur);

    }

    @Override
    public void update(Observable o, Object arg) {
        Desert desert = (Desert) o; // On récupère le modèle pour suivre son nouvel état
        this.actionLabel.setText("Actions restantes : " + joueur.getNbActions());
        this.gourdeLabel.setText("Gourdes restantes : " + joueur.getGourde());
    }


}




