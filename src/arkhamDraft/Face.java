package arkhamDraft;

import javax.swing.*;
import java.awt.*;


public class Face extends javax.swing.JFrame{
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel draftPanel;
    private JPanel draftedCardsPanel;
    private JPanel deckPanel;
    private JPanel sideboardPanel;


    public void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        buttonPanel = new JPanel();
        mainPanel.add(buttonPanel, gbc);

        JButton startDraftButton = new JButton("Start draft");
        buttonPanel.add(startDraftButton, gbc);

        JButton otherButton = new JButton("Something else");
        buttonPanel.add(otherButton, gbc);
        otherButton.addActionListener(e -> System.out.println("You pressed the other Button."));

        gbc.gridy++;
        draftPanel = new JPanel();
        draftPanel.setLayout(new GridBagLayout());
        mainPanel.add(draftPanel, gbc);
        GridBagConstraints gbcDraftPanel = new GridBagConstraints();
        gbcDraftPanel.gridx = 0;
        gbcDraftPanel.gridy = 0;
        gbcDraftPanel.insets = new Insets(2, 2, 2, 2);

        gbcDraftPanel.fill = GridBagConstraints.HORIZONTAL;
        gbcDraftPanel.gridwidth = 3;
        draftPanel.add(new JLabel("Current draft deck"), gbcDraftPanel);
        gbcDraftPanel.gridwidth = 1;

        gbcDraftPanel.gridy++;
        draftedCardsPanel = new JPanel();
        draftPanel.add(draftedCardsPanel, gbcDraftPanel);
        draftedCardsPanel.add(new JLabel("draftpanel"));

        gbcDraftPanel.gridx++;
        deckPanel = new JPanel();
        draftPanel.add(deckPanel, gbcDraftPanel);
        deckPanel.add(new JLabel("deckpanel"));

        gbcDraftPanel.gridx++;
        sideboardPanel = new JPanel();
        draftPanel.add(sideboardPanel, gbcDraftPanel);
        sideboardPanel.add(new JLabel("sideboardpanel"));


        this.add(mainPanel);

    }
}
