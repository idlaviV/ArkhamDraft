package arkhamDraft;

import arkhamDraft.workerPool.*;

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
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3,1,100,1);
        draftButtonPanel.add(initializeDraftCardsButton(spinnerModel));
        draftButtonPanel.add(new JSpinner(spinnerModel));


        JPanel otherButtonsPanel = new JPanel();
        otherButtonsPanel.add(initializeRedraftButton());
        otherButtonsPanel.add(initializeAddButton());
        otherButtonsPanel.add(initializeAddSideboardButton());

        JPanel draftActionsPanel = new JPanel();
        draftActionsPanel.setLayout(new BoxLayout(draftActionsPanel, BoxLayout.Y_AXIS));
        draftButtonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        otherButtonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftActionsPanel.add(draftButtonPanel);
        draftActionsPanel.add(otherButtonsPanel);
        return draftActionsPanel;
    }

    private Component initializeAddSideboardButton() {
        JButton addSideboardButton = new JButton("To Sideboard");
        addSideboardButton.addActionListener(e -> new AddSideboardButtonWorker(
                brain,
                draftedCardsList,
                (deck) -> {
                    printCardsToDraftPanel(deck);
                    return null;
                },
                (deck) -> {
                    printCardsToSideboardPanel(deck);
                    return null;
                }
        ).execute());
        return addSideboardButton;
    }

    private Component initializeAddButton() {
        JButton addButton = new JButton("Add");
        addButton.addActionListener(
                e -> new AddButtonWorker(
                        brain,
                        draftedCardsList,
                        (deck) -> {
                            printCardsToDraftPanel(deck);
                            return null;
                        },
                        (deck) -> {
                            printCardsToDeckPanel(deck);
                            return null;
                        }
                ).execute());
        return addButton;
    }

    private Component initializeRedraftButton() {
        JButton redraftButton = new JButton("Redraft");
        redraftButton.addActionListener(
                e-> new RedraftButtonWorker(
                        brain,
                        draftedCardsList,
                        (deck)-> {
                            printCardsToDraftPanel(deck);
                            return null;
                        }
                ).execute()
        );
        return redraftButton;
    }

    private Component initializeDraftCardsButton(SpinnerNumberModel spinnerModel) {
        JButton draftCardsButton = new JButton("Draft");
        draftCardsButton.addActionListener(
            e -> new DraftCardsButtonWorker(
                brain,
                spinnerModel.getNumber().intValue(),
                (deck, list) -> {
                    printCardsToPanel(deck, list);
                    return null;
                },
                draftedCardsList
            ).execute()
        );
        return draftCardsButton;
    }

    private Component initializeDeckPanel(){
        JPanel deckPanel = new JPanel();
        deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.PAGE_AXIS));
        deckPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        deckPanel.add(new JLabel("deck panel"));
        deckList = new CardCheckBoxList();
        JScrollPane deckScrollPane = new JScrollPane(deckList);
        deckPanel.add(deckScrollPane);

        DefaultComboBoxModel<String> sortComboBoxModel = new DefaultComboBoxModel<>();
        JComboBox<String> sortComboBox = new JComboBox<>(sortComboBoxModel);
        deckPanel.add(initializeSortDeckButton(sortComboBox));
        deckPanel.add(sortComboBox);
        sortComboBoxModel.addElement("Type, then name");
        sortComboBoxModel.addElement("Just name");
        sortComboBoxModel.addElement("Faction, then name");
        sortComboBoxModel.addElement("Faction, then XP, then name");
        sortComboBoxModel.addElement("XP, then name");
        sortComboBoxModel.addElement("Cost, then name");

        return deckPanel;
    }

    private Component initializeSortDeckButton(JComboBox<String> sortComboBox) {
        JButton sortDeckButton = new JButton("Sort Deck");
        sortDeckButton.addActionListener(e -> new SortDeckButtonWorker(
                brain,
                sortComboBox,
                (deck) -> {
                    printCardsToDeckPanel(deck);
                    return null;
                }
        ).execute());
        return sortDeckButton;
    }

    private Component initializeSideBoardPanel() {
        JPanel sideboardPanel = new JPanel();
        JPanel sideboardButtonPanel = new JPanel();
        sideboardButtonPanel.add(initializeAddFromSideBoardButton());
        sideboardPanel.setLayout(new BoxLayout(sideboardPanel, BoxLayout.PAGE_AXIS));
        sideboardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        sideboardPanel.add(new JLabel("sideboard panel"));
        sideboardList = new CardCheckBoxList();
        JScrollPane sideboardScrollPane = new JScrollPane(sideboardList);
        sideboardPanel.add(sideboardScrollPane);
        sideboardPanel.add(sideboardButtonPanel);
        return sideboardPanel;
    }

    private Component initializeAddFromSideBoardButton() {
        JButton addFromSideBoardButton = new JButton("Add");
        addFromSideBoardButton.addActionListener(e ->
                new AddFromSideBoardButtonWorker(
                        brain,
                        sideboardList,
                        (deck) -> {
                            printCardsToDeckPanel(deck);
                            return null;
                        },
                        (deck) -> {
                            printCardsToSideboardPanel(deck);
                            return null;
                        }
                ).execute());
        return addFromSideBoardButton;
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
