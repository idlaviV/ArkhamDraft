package arkhamDraft;

import javax.swing.*;


public class Face extends javax.swing.JFrame{
    private JPanel mainPanel;


    public void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new java.awt.FlowLayout());

        JButton startDraftButton = new JButton("Start draft");
        mainPanel.add(startDraftButton);

        JButton otherButton = new JButton("Something else");
        mainPanel.add(otherButton);
        otherButton.addActionListener(e -> System.out.println("You pressed the other Button."));

        this.add(mainPanel);

    }
}
