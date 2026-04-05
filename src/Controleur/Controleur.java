package Controleur;

import Modele.Desert;
import Vue.VGrille;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controleur implements ActionListener {

    Desert desert;
    public Controleur(Desert desert) {
        this.desert = desert;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JButton) {
            JButton btn = (JButton) source;
            if (btn.getText().equals("Finir le tour")) {
                desert.finDuTour();
            }
        }

    }
}