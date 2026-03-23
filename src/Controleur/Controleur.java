package Controleur;

import Conway.CModele;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controleur implements ActionListener {

    CModele modele;
    Controleur(CModele modele) {
        this.modele = modele;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
