package Vue;

import javax.swing.*;
import java.awt.*;

public class VScoreboardALL extends JPanel {
    public VScoreboardALL(int nbJoueurs) {

        setLayout(new GridLayout(nbJoueurs, 1));
    }

    public void addScoreBoard(VScoreBoardSingle panel) {
        add(panel);
    }
}

