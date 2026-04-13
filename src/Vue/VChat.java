package Vue;

import Modele.Desert;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;


import javax.swing.*;


public class VChat extends JScrollPane implements Observer {
    private JTextArea chatArea;
    public VChat(Desert desert){
        super();
        this.chatArea = new JTextArea();
        this.chatArea.setEditable(false); // Make chat area read-only
        this.chatArea.setLineWrap(true); // Enable line wrapping
        this.chatArea.setBackground(new Color(255, 248, 220)); // Fond sable clair
        this.setViewportView(this.chatArea);
        this.setPreferredSize(new Dimension(300, 150)); // Set a preferred size for the chat panel
        this.setBorder(BorderFactory.createTitledBorder("Journal de Bord"));

        desert.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        //si l'argument envoyé par notifyObservers est un String on l'affiche
        if (arg instanceof String) {
            addMessage((String) arg);
        }
    }



    public void addMessage(String message) {
        this.chatArea.append(message + "\n");
        // The AdjustmentListener handles auto-scrolling, so explicit call is not needed
    }


    public void clearChat() {
        this.chatArea.setText("");
    }



}
