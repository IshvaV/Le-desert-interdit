package Vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;


public class VChat extends JScrollPane {
    private JTextArea chatArea;
    public VChat(){
        super();
        this.chatArea = new JTextArea();
        this.chatArea.setEditable(false); // Make chat area read-only
        this.chatArea.setLineWrap(true); // Enable line wrapping
        this.setViewportView(this.chatArea);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setPreferredSize(new Dimension(300, 50)); // Set a preferred size for the chat panel

    }



    public void addMessage(String message) {
        this.chatArea.append(message + "\n");
        // The AdjustmentListener handles auto-scrolling, so explicit call is not needed
    }


    public void clearChat() {
        this.chatArea.setText("");
    }



}
