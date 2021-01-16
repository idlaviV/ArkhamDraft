package arkhamDraft;

import arkhamDraft.workerPool.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Face extends JFrame{
    private JPanel draftPanel;
    private CardCheckBoxList deckList;
    private CardCheckBoxList sideboardList;
    private CardCheckBoxList draftedCardsList;
    private final Brain brain;
    private NewDraftDeckDialog newDraftDeckDialog;
    EverythingDisablerAndReenabler draftEnabler;
    private boolean draftEnabled = false;
    private final JFileChooser fc = new JFileChooser();


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
        newDraftDeckDialog = new NewDraftDeckDialog(brain, this::printCardsToDeckPanel, this::enableDraftPanel);
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
                this::printCardsToDraftPanel,
                this::printCardsToSideboardPanel
        ).execute());
        return addSideboardButton;
    }

    private Component initializeAddButton() {
        JButton addButton = new JButton("Add");
        addButton.addActionListener(
                e -> new AddButtonWorker(
                        brain,
                        draftedCardsList,
                        this::printCardsToDraftPanel,
                        this::printCardsToDeckPanel
                ).execute());
        return addButton;
    }

    private Component initializeRedraftButton() {
        JButton redraftButton = new JButton("Redraft");
        redraftButton.addActionListener(
                e-> new RedraftButtonWorker(
                        brain,
                        draftedCardsList,
                        this::printCardsToDraftPanel
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
                    this::printCardsToPanel,
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

        DefaultComboBoxModel<String> sortComboBoxModel = initializeSortComboBoxModel();
        JComboBox<String> sortComboBox = new JComboBox<>(sortComboBoxModel);
        deckPanel.add(initializeSortDeckButton(sortComboBox));
        deckPanel.add(sortComboBox);

        deckPanel.add(initializeSaveLoadDeckPanel());


        return deckPanel;
    }

    private Component initializeSaveLoadDeckPanel() {
        JPanel saveLoadDeckPanel = new JPanel();
        saveLoadDeckPanel.add(initializeSaveButton());
        return saveLoadDeckPanel;
    }

    private Component initializeSaveButton() {
        JButton loadButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/actions-document-save-icon.png"));
            loadButton.setIcon(new ImageIcon(img));
            loadButton.setMargin(new Insets(0, 0, 0, 0));
            loadButton.setBorder(null);
            loadButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadButton.addActionListener(e -> {
            File directory = new File("decks");
            if (!directory.exists()) {
                directory.mkdir();
            }
            fc.setCurrentDirectory(directory);
            int returnVal = fc.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String pathName = file.toPath().toString();
                if (!pathName.endsWith(".txt")) {
                    file = new File(pathName + ".txt");
                    new SaveDeckButtonWorker(brain, file).execute();
                }
            }
        });
        return loadButton;
    }

    private DefaultComboBoxModel<String> initializeSortComboBoxModel() {
        DefaultComboBoxModel<String> sortComboBoxModel = new DefaultComboBoxModel<>();
        sortComboBoxModel.addElement("Type, then name");
        sortComboBoxModel.addElement("Just name");
        sortComboBoxModel.addElement("Faction, then name");
        sortComboBoxModel.addElement("Faction, then XP, then name");
        sortComboBoxModel.addElement("XP, then name");
        sortComboBoxModel.addElement("Cost, then name");
        return sortComboBoxModel;
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
                        this::printCardsToDeckPanel,
                        this::printCardsToSideboardPanel
                ).execute());
        return addFromSideBoardButton;
    }

    public void enableDraftPanel() {
        if (!draftEnabled) {
            draftEnabled = true;
            enableDraftPanel(true);
        }
    }

    private void enableDraftPanel(boolean b) {
        if (b) {
            draftEnabler.reenable();
        } else {
            draftEnabler.disable();
        }
    }


}
