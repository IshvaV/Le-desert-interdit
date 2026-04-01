package Vue;

import Modele.Desert;
import Modele.Difficulte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launcher implements ActionListener {
    private JFrame frame;
  //  private Fenetre fenetre;
    private JButton button;
    private JPanel panel;
    public Launcher() {
        frame = new JFrame("LAUNCHER");
        frame.setSize(800, 600);

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        frame.add(panel);





        button = new JButton("START");
        button.addActionListener(this);
        button.setSize(100, 50);
        button.setLocation(panel.getLocation().x + panel.getWidth(), panel.getLocation().y + panel.getHeight());


        panel.add(button);



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);



        frame.setVisible(true);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            Desert modele = new Desert(Difficulte.NOVICE, 1);
            new Fenetre(modele);
        }
    }
}
