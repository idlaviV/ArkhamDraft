package arkhamDraft;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public class Face extends JFrame{
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel draftPanel;
    private JPanel draftedCardsPanel;
    private JPanel sideboardPanel;
    private DefaultListModel<String> draftedCardsListM;
    private DefaultListModel<String> deckListM;
    private DefaultListModel<String> sideboardListM;


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
        initializeDraftPanel(gbc);


        getContentPane().add(mainPanel);
        this.setVisible(true);

    }

    private void initializeDraftPanel(GridBagConstraints gbc) {
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
        draftedCardsListM = new DefaultListModel<>();
        draftedCardsListM.addElement("testentry");
        JList<String> draftedCardsList = new JList<>(draftedCardsListM);
        draftedCardsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane draftScrollPane = new JScrollPane(draftedCardsList);
        draftedCardsPanel.add(draftScrollPane);



        gbcDraftPanel.gridx++;
        JPanel deckPanel = new JPanel();
        deckPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftPanel.add(deckPanel, gbcDraftPanel);
        deckPanel.add(new JLabel("deckpanel"));
        deckListM = new DefaultListModel<>();
        deckListM.addElement("a test card");
        deckListM.addElement("a test card");
        JList<String> deckList = new JList<>(deckListM);
        JScrollPane deckScrollPane = new JScrollPane(deckList);
        deckPanel.add(deckScrollPane);

        gbcDraftPanel.gridx++;
        sideboardPanel = new JPanel();
        sideboardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftPanel.add(sideboardPanel, gbcDraftPanel);
        sideboardPanel.add(new JLabel("sideboardpanel"));
        sideboardListM = new DefaultListModel<>();
        sideboardListM.addElement("testcard");
        JList<String> sideboardList = new JList<>(sideboardListM);
        JScrollPane sideboardScrollPane = new JScrollPane(sideboardList);
        sideboardPanel.add(sideboardScrollPane);
    }

    public void updateDraftedCardsList(ArrayList<String> arguments) {

    }
}
