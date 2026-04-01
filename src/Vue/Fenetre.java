package Vue;

import Conway.CModele;
import Modele.Desert;

import javax.swing.*;
import java.awt.*;

public class Fenetre {
    private JFrame frame;

    private VGrille vgrille;
    ///private VCommandes vcommandes;
    public Fenetre(Desert desert) {
        frame = new JFrame();
        frame.setTitle("Fenetre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        vgrille = new VGrille(desert);
        frame.add(vgrille);
       // vcommandes = new VCommandes(desert);
        //frame.add(vcommandes);
        frame.pack();
        frame.setVisible(true);

    }


}
