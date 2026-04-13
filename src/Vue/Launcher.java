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
    private JComboBox buttonDifficulty;
    private JButton buttonStart;
    private JComboBox buttonJoueur;
    private JPanel panel;
    private Desert modele;

    public Launcher() {
        frame = new JFrame("LAUNCHER");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width*9/16,screenSize.height*9/16);




        panel = new JPanel(null){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image bg = new ImageIcon("src/data/Desert-image.png").getImage();
                g.drawImage(bg, 0, 0,getWidth() ,getHeight(), this);
            }
        };

        frame.add(panel);
        this.buttonStart = setButton("START", frame.getWidth()/2, frame.getHeight()/2);

        this.buttonDifficulty = setComboBox(new String[]{"NOVICE","NORMAL","ELITE","LEGENDAIRE"},"DIFFICULTY", frame.getWidth()/2, frame.getHeight()/2+100);

        this.buttonJoueur = setComboBox(new String[]{"1","2","3","4"}, "PLAYERS", frame.getWidth()/2, frame.getHeight()/2+200);



        panel.add(buttonDifficulty);
        panel.add(buttonStart);
        panel.add(buttonJoueur);


        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);



        frame.setVisible(true);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonStart) {
            Difficulte difficulte;
            int joueur;

            // Gestion de la difficulté (Défaut : NORMAL)
            switch (buttonDifficulty.getSelectedIndex()) {
                case 1:  difficulte = Difficulte.NOVICE; break;
                case 3:  difficulte = Difficulte.ELITE; break;
                case 4:  difficulte = Difficulte.LEGENDAIRE; break;
                case 2:
                default: difficulte = Difficulte.NORMAL; break;
            }

            // Gestion du nombre de joueurs (Défaut : 2)
            switch (buttonJoueur.getSelectedIndex()) {
                case 1:  joueur = 1; break;
                case 3:  joueur = 3; break;
                case 4:  joueur = 4; break;
                case 2:
                default: joueur = 2; break;
            }

            modele = new Desert(difficulte, joueur);
            new Fenetre(modele);
            this.frame.dispose();
        }
    }
    public JButton setButton(String Name, int x, int y) {
        JButton button = new JButton( Name);
        button.addActionListener(this);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(102, 51, 0));
        button.setFocusPainted(false);
        button.setSize(button.getPreferredSize());
        button.setLocation(x-button.getWidth()/2,y-button.getHeight()/2);
        return button;
    }
    public JComboBox setComboBox(String[] items,String titre,int x,int y) {
        JComboBox comboBox = new JComboBox();

        comboBox.addItem(titre);
        for (String item : items) {
            comboBox.addItem(item);

        }

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setEnabled(index !=0);
                return this;
            }
        });


        comboBox.setFont(new Font("Arial", Font.BOLD, 20));
        comboBox.setForeground(Color.WHITE);
        comboBox.setBackground(new Color(102, 51, 0));






        comboBox.addActionListener(this);
        comboBox.setSize(comboBox.getPreferredSize());
        comboBox.setLocation(x-comboBox.getWidth()/2,y-comboBox.getHeight()/2);
        return comboBox;
    }
}
