package Controleur;

import Modele.Desert;
import Modele.ModeAction;
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
            String text = btn.getText();
            switch(text) {
                case "Finir le tour":
                    desert.finDuTour();
                    break;
                case "Se déplacer":
                    desert.setModeActuel(ModeAction.DEPLACEMENT);
                    break;
                case "Creuser":
                    desert.setModeActuel(ModeAction.CREUSER);
                    break;
                case "Explorer":
                    desert.explorer();
                    break;
                case "Ramasser":
                    desert.ramasserPiece();
                    break;
                case "Donner de l'eau":
                    desert.setModeActuel(ModeAction.DONNER_EAU);
                    break;
                case "Puiser":
                    desert.puiserEau();
                    break;
                case "Déplacer collègue":
                    desert.setModeActuel(ModeAction.NAVIGATE_CIBLE);
                    break;
                }
        }

    }
}