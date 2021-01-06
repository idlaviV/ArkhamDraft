package arkhamDraft;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class Face extends JFrame{
    private JPanel draftPanel;
    private CardCheckBoxList deckList;
    private CardCheckBoxList sideboardList;
    private CardCheckBoxList draftedCardsList;
    private final Brain brain;
    private NewDraftDeckDialog newDraftDeckDialog;
    EverythingDisablerAndReenabler draftEnabler;


    public Face(Brain brain) {
        super();
        this.brain = brain;
    }


    public void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        JPanel buttonPanel = new JPanel();
        mainPanel.add(buttonPanel, gbc);

        JButton startDraftButton = new JButton("Start draft");
        buttonPanel.add(startDraftButton);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JButton newDraftDeckButton = new JButton("New draft deck");
        buttonPanel.add(newDraftDeckButton);
        newDraftDeckDialog = new NewDraftDeckDialog(brain);
        newDraftDeckButton.addActionListener(e -> {
            brain.guiOpensNewDraftDeckDialog();
            newDraftDeckDialog.tidyUp();
            newDraftDeckDialog.setVisible(true);
        });

        JButton otherButton = new JButton("Something else");
        buttonPanel.add(otherButton);
        otherButton.addActionListener(e -> System.out.println("You pressed the other Button."));

        gbc.gridy++;
        mainPanel.add(initializeDraftPanel(), gbc);

        add(mainPanel);
        draftEnabler = new EverythingDisablerAndReenabler(draftPanel, null);
        draftEnabler.disable();
        setVisible(true);
    }

    private Component initializeDraftPanel() {
        draftPanel = new JPanel();
        draftPanel.setLayout(new GridBagLayout());
        draftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        GridBagConstraints gbcDraftPanel = new GridBagConstraints();
        gbcDraftPanel.gridx = 0;
        gbcDraftPanel.gridy = 0;
        gbcDraftPanel.insets = new Insets(2, 2, 2, 2);

        gbcDraftPanel.gridwidth = 3;
        draftPanel.add(new JLabel("Current draft deck"), gbcDraftPanel);
        gbcDraftPanel.gridwidth = 1;

        gbcDraftPanel.gridy++;
        draftPanel.add(initializeDraftedCardsPanel(), gbcDraftPanel);

        gbcDraftPanel.gridx++;
        draftPanel.add(initializeDeckPanel(), gbcDraftPanel);

        gbcDraftPanel.gridx++;
        draftPanel.add(initializeSideBoardPanel(), gbcDraftPanel);

        return draftPanel;
    }


    public void fillWithCards(CardCheckBoxList list) {
        for (int i = 0; i<15; i++) {
            list.addCard(new Card("Dunwich", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", null, 2));
        }
    }

    public void printCardsToDraftPanel(Deck deck) {
        printCardsToPanel(deck, draftedCardsList);
    }

    public void printCardsToDeckPanel(Deck deck) {
        printCardsToPanel(deck, deckList);
    }

    public void printCardsToSideboardPanel(Deck deck) {
        printCardsToPanel(deck, sideboardList);
    }

    private void printCardsToPanel(Deck deck, CardCheckBoxList list) {
        EventQueue.invokeLater(() -> {
            list.clearList();
            for (Card card : deck.getCards()) {
                if (!card.equals(Card.nullCard)) {
                    list.addCard(card);
                }
            }
        });
    }

    private Component initializeDraftedCardsPanel() {
        JPanel draftedCardsPanel = new JPanel();
        draftedCardsPanel.setLayout(new BoxLayout(draftedCardsPanel, BoxLayout.PAGE_AXIS));
        draftedCardsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftedCardsPanel.add(new JLabel("draft panel"));
        draftedCardsList = new CardCheckBoxList();
        JScrollPane draftScrollPane = new JScrollPane(draftedCardsList);
        draftedCardsPanel.add(draftScrollPane);

        draftedCardsPanel.add(initializeDraftActionsPanel());
        return draftedCardsPanel;
    }

    private Component initializeDraftActionsPanel() {
        JPanel draftButtonPanel = new JPanel();
        JButton draftCardsButton = new JButton("Draft");
        draftButtonPanel.add(draftCardsButton);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3,1,100,1);
        draftButtonPanel.add(new JSpinner(spinnerModel));
        draftCardsButton.addActionListener(e -> brain.guiDraftCards((int) spinnerModel.getNumber()));

        JPanel otherButtonsPanel = new JPanel();
        JButton redraftButton = new JButton("Redraft");
        JButton addButton = new JButton("Add");
        JButton addSideboardButton = new JButton("To Sideboard");
        otherButtonsPanel.add(redraftButton);
        otherButtonsPanel.add(addButton);
        otherButtonsPanel.add(addSideboardButton);

        redraftButton.addActionListener(e -> EventQueue.invokeLater(() -> brain.guiRedraft(draftedCardsList.getCheckedCards())));
        addButton.addActionListener(e -> EventQueue.invokeLater(()-> brain.guiAddToDeck(draftedCardsList.getCheckedCards())));
        addSideboardButton.addActionListener(e -> EventQueue.invokeLater(() -> brain.guiAddToSideboard(draftedCardsList.getCheckedCards())));

        JPanel draftActionsPanel = new JPanel();
        draftActionsPanel.setLayout(new BoxLayout(draftActionsPanel, BoxLayout.Y_AXIS));
        draftButtonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        otherButtonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftActionsPanel.add(draftButtonPanel);
        draftActionsPanel.add(otherButtonsPanel);
        return draftActionsPanel;
    }

    private Component initializeDeckPanel(){
        JPanel deckPanel = new JPanel();
        deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.PAGE_AXIS));
        deckPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        deckPanel.add(new JLabel("deck panel"));
        deckList = new CardCheckBoxList();
        JScrollPane deckScrollPane = new JScrollPane(deckList);
        deckPanel.add(deckScrollPane);

        JButton sortDeckButton = new JButton("Sort Deck");
        deckPanel.add(sortDeckButton);

        DefaultComboBoxModel<String> sortComboBoxModel = new DefaultComboBoxModel<>();
        JComboBox<String> sortComboBox = new JComboBox<>(sortComboBoxModel);
        deckPanel.add(sortComboBox);
        sortComboBoxModel.addElement("Type, then name");
        sortComboBoxModel.addElement("Just name");
        sortComboBoxModel.addElement("Faction, then name");
        sortComboBoxModel.addElement("Faction, then XP, then name");
        sortComboBoxModel.addElement("XP, then name");
        sortComboBoxModel.addElement("Cost, then name");
        sortDeckButton.addActionListener(e -> brain.sortDeck((String) Objects.requireNonNull(sortComboBox.getSelectedItem())));

        return deckPanel;
    }

    private Component initializeSideBoardPanel() {
        JPanel sideboardPanel = new JPanel();
        JPanel sideboardButtonPanel = new JPanel();
        JButton addFromSideBoardButton = new JButton("Add");
        sideboardButtonPanel.add(addFromSideBoardButton);
        addFromSideBoardButton.addActionListener(e -> EventQueue.invokeLater(() -> brain.guiAddFromSideboard(sideboardList.getCheckedCards())));
        sideboardPanel.setLayout(new BoxLayout(sideboardPanel, BoxLayout.PAGE_AXIS));
        sideboardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        sideboardPanel.add(new JLabel("sideboard panel"));
        sideboardList = new CardCheckBoxList();
        JScrollPane sideboardScrollPane = new JScrollPane(sideboardList);
        sideboardPanel.add(sideboardScrollPane);
        sideboardPanel.add(sideboardButtonPanel);
        return sideboardPanel;
    }

    public void enableDraftPanel(boolean b) {
        if (b) {
            draftEnabler.reenable();
        } else {
            draftEnabler.disable();
        }
    }

    public void addFilterToFilterList(CardFilter newCardFilter) {
        newDraftDeckDialog.addFilterToFilterList(newCardFilter);
    }

    public void updateDraftDeckLog(String newLabel) {
        newDraftDeckDialog.updateDraftDeckLog(newLabel);
    }

    public void updateDraftDeckSize(int newSize) {
        newDraftDeckDialog.updateDraftDeckSize(newSize);
    }

    public void updateCurrentCardsFiltered(int cardsFilteredByFilterListSize) {
        newDraftDeckDialog.updateCurrentCardsFiltered(cardsFilteredByFilterListSize);
    }

    public void draftingBoxWasDiscarded() {
        newDraftDeckDialog.draftingBoxWasDiscarded();
    }
}
