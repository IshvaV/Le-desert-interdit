package Controleur;

import Modele.Desert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controleur implements ActionListener {

    Desert desert;
    public Controleur(Desert desert) {
        this.desert = desert;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}