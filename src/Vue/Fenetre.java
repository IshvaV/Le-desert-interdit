package Vue;

import Controleur.Controleur;
import Modele.Desert;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;

public class Fenetre {
    private JFrame frame;
    private JPanel MainPanel;
    private VScoreBoardSingle[] scoreboard;
    private VScoreboardALL scoreboardall;
    private VGrille vgrille;
    public static VChat vchat;
    private Controleur controleur;
    private VCommandes finirTourButton;
    private JPanel statusJeu;

    public Fenetre(Desert desert) {
        frame = new JFrame();
        frame.setTitle("Fenetre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        MainPanel = new JPanel();
        MainPanel.setLayout(new BorderLayout());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        this.controleur = new Controleur(desert);

        this.finirTourButton = new VCommandes(desert, controleur);
        this.finirTourButton.setVisible(true);
        bottomPanel.add(this.finirTourButton, BorderLayout.CENTER);


        scoreboard = new VScoreBoardSingle[desert.getNbJoueurs()];
        vgrille = new VGrille(desert);
        this.scoreboardall = new VScoreboardALL(desert.getNbJoueurs());

        for (int i = 0; i < desert.getNbJoueurs(); i++) {
            scoreboard[i] = new VScoreBoardSingle(desert,desert.getJoueurs()[i]);
            scoreboardall.add(scoreboard[i]);
        }


        MainPanel.add(vgrille, BorderLayout.CENTER);
        vchat = new VChat(desert);

        bottomPanel.add(vchat, BorderLayout.SOUTH);

        MainPanel.add(bottomPanel, BorderLayout.SOUTH);
        MainPanel.add(scoreboardall, BorderLayout.EAST);

        //frame.add(vchat, BorderLayout.CENTER);
       // vcommandes = new VCommandes(desert);

        //frame.add(vcommandes);
        frame.add(MainPanel, BorderLayout.CENTER);

        statusJeu = new VGameStatus(desert);
        frame.add(statusJeu, BorderLayout.NORTH);



        frame.setResizable(false);

        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }


}
