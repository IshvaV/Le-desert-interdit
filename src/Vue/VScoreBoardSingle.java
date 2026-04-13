package Vue;

import Controleur.Controleur;
import Modele.Desert;
import Modele.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VScoreBoardSingle extends JPanel implements Observer {

    private JLabel titre;
    private Joueur joueur;


    private JLabel actionLabel;
    private JLabel gourdeLabel;

    //private JButton endTurnButton;
    public VScoreBoardSingle(Desert desert, Joueur joueur) {
        this.joueur = joueur;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(244, 164, 96));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        desert.addObserver(this);

        titre = new JLabel("Joueur " + (joueur.getId() + 1) + " : " + joueur.getRole().getNom());
        titre.setFont(new Font("Arial", Font.BOLD, 14));

        // Indicateur coloré
        JPanel colorIcon = new JPanel();
        colorIcon.setBackground(joueur.getId() == 0 ? Color.YELLOW : joueur.getId() == 1 ? Color.RED : joueur.getId() == 2 ? Color.BLUE : Color.GREEN);
        colorIcon.setPreferredSize(new Dimension(15, 15));

        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titleBar.setOpaque(false);
        titleBar.add(colorIcon);
        titleBar.add(titre);

        this.actionLabel = new JLabel("Actions : " + joueur.getActionsDispo());
        this.gourdeLabel = new JLabel("Gourde : " + joueur.getGourde() + "/" + joueur.getRole().getMaxGourde());

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(this.actionLabel);
        infoPanel.add(this.gourdeLabel);

        this.add(titleBar);
        this.add(infoPanel);
        
        update(desert, null); // Initialisation visuelle
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Desert desert) {
            this.actionLabel.setText("Actions : " + joueur.getActionsDispo());
            this.gourdeLabel.setText("Gourde : " + joueur.getGourde() + "/" + joueur.getRole().getMaxGourde());

            if (desert.getJoueurAct() == joueur.getId()) {
                this.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            } else {
                this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            }
        }
    }


}




