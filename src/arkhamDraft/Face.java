package arkhamDraft;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Face extends JFrame{
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel draftPanel;
    private JPanel draftedCardsPanel;
    private JPanel sideboardPanel;
    private CardCheckBoxList draftedCardsList;
    private CardCheckBoxList deckList;
    private CardCheckBoxList sideboardList;


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


        add(mainPanel);
        setVisible(true);

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
        draftedCardsPanel.setLayout(new BoxLayout(draftedCardsPanel, BoxLayout.PAGE_AXIS));
        draftedCardsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftPanel.add(draftedCardsPanel, gbcDraftPanel);
        draftedCardsPanel.add(new JLabel("draft panel"));
        draftedCardsList = new CardCheckBoxList();
        JScrollPane draftScrollPane = new JScrollPane(draftedCardsList);
        draftedCardsPanel.add(draftScrollPane);



        gbcDraftPanel.gridx++;
        JPanel deckPanel = new JPanel();
        deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.PAGE_AXIS));
        deckPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftPanel.add(deckPanel, gbcDraftPanel);
        deckPanel.add(new JLabel("deck panel"));
        deckList = new CardCheckBoxList();
        JScrollPane deckScrollPane = new JScrollPane(deckList);
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckList.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", 2));
        deckPanel.add(deckScrollPane);

        gbcDraftPanel.gridx++;
        sideboardPanel = new JPanel();
        sideboardPanel.setLayout(new BoxLayout(sideboardPanel, BoxLayout.PAGE_AXIS));
        sideboardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftPanel.add(sideboardPanel, gbcDraftPanel);
        sideboardPanel.add(new JLabel("sideboard panel"));
        /*sideboardListM = new DefaultListModel<>();
        sideboardListM.addElement("testcard");
        JList<String> sideboardList = new JList<>(sideboardListM);
        JScrollPane sideboardScrollPane = new JScrollPane(sideboardList);
        sideboardPanel.add(sideboardScrollPane);*/
        sideboardList = new CardCheckBoxList();
        JScrollPane sideboardScrollPane = new JScrollPane(sideboardList);
        sideboardPanel.add(sideboardScrollPane);
    }

    public void updateDraftedCardsList(ArrayList<String> arguments) {

    }
}
