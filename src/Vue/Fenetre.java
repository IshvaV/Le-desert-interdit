package Vue;

import Modele.Desert;

import javax.swing.*;
import java.awt.*;

public class Fenetre {
    private JFrame frame;
    private VScoreBoardSingle[] scoreboard;
    private VScoreboardALL scoreboardall;
    private VGrille vgrille;
    public static VChat vchat;


    public Fenetre(Desert desert) {
        frame = new JFrame();
        frame.setTitle("Fenetre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Changed from DISPOSE_ON_CLOSE
        frame.setLayout(new BorderLayout());




        scoreboard = new VScoreBoardSingle[desert.getNbJoueurs()];
        vgrille = new VGrille(desert);
        this.scoreboardall = new VScoreboardALL(desert.getNbJoueurs());
        for (int i = 0; i < desert.getNbJoueurs(); i++) {
            scoreboard[i] = new VScoreBoardSingle(desert.getJoueurs()[i]);
            scoreboardall.add(scoreboard[i]);
        }
        frame.add(vgrille, BorderLayout.CENTER);
        vchat = new VChat();
        frame.add(vchat, BorderLayout.SOUTH);
        frame.add(scoreboardall, BorderLayout.EAST);

        //frame.add(vchat, BorderLayout.CENTER);
       // vcommandes = new VCommandes(desert);

        //frame.add(vcommandes);

        frame.setResizable(false);

        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }


}
