package Vue;

import Modele.Joueur;

import javax.swing.*;

public class VScoreBoardSingle extends JPanel {

    private JLabel title;


    private JLabel actionLabel;
    private JLabel gourdeLabel;

    private JButton endTurnButton;
    public VScoreBoardSingle(Joueur joueur) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));


        title = new JLabel("Joueur " + (joueur.getId()+1) );
        title.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        this.add(title);
        this.add(Box.createVerticalStrut(10)); // Add some space

        actionLabel = new JLabel("Actions restantes : " + joueur.getNbActions());
        this.add(actionLabel);
        this.add(Box.createVerticalStrut(5));

        gourdeLabel = new JLabel("Gourdes restantes : " + joueur.getGourde());
        this.add(gourdeLabel);

        // Add a button to end the turn
        endTurnButton = new JButton("Finir le tour");
        this.add(endTurnButton); //t
        endTurnButton.addActionListener(e -> Fenetre.vchat.addMessage("Le joueur " + (joueur.getId() + 1) + " a fini son tour."));


    }


}




