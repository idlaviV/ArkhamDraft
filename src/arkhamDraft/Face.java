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
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        buttonPanel = new JPanel();
        mainPanel.add(buttonPanel, gbc);

        JButton startDraftButton = new JButton("Start draft");
        buttonPanel.add(startDraftButton);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JButton otherButton = new JButton("Something else");
        buttonPanel.add(otherButton);
        otherButton.addActionListener(e -> System.out.println("You pressed the other Button."));

        gbc.gridy++;
        draftPanel = new JPanel();
        draftPanel.setLayout(new GridBagLayout());
        draftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.add(draftPanel, gbc);
        GridBagConstraints gbcDraftPanel = new GridBagConstraints();
        gbcDraftPanel.gridx = 0;
        gbcDraftPanel.gridy = 0;
        gbcDraftPanel.insets = new Insets(2, 2, 2, 2);

        gbcDraftPanel.gridwidth = 3;
        draftPanel.add(new JLabel("Current draft deck"), gbcDraftPanel);
        gbcDraftPanel.gridwidth = 1;

        gbcDraftPanel.gridy++;
        draftedCardsPanel = new JPanel();
        draftedCardsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftPanel.add(draftedCardsPanel, gbcDraftPanel);
        draftedCardsPanel.add(new JLabel("draftpanel"));

        gbcDraftPanel.gridx++;
        deckPanel = new JPanel();
        deckPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftPanel.add(deckPanel, gbcDraftPanel);
        deckPanel.add(new JLabel("deckpanel"));

        gbcDraftPanel.gridx++;
        sideboardPanel = new JPanel();
        sideboardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftPanel.add(sideboardPanel, gbcDraftPanel);
        sideboardPanel.add(new JLabel("sideboardpanel"));


        this.add(mainPanel);

    }
}
