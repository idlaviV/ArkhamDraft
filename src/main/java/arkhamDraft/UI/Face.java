package arkhamDraft.UI;

import arkhamDraft.*;
import arkhamDraft.UI.workerPool.*;
import arkhamDraft.UI.workerPool.OpenNewDraftDeckDialogWorker;
import arkhamDraft.UI.workerPool.StartDraftButtonWorker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Face extends JFrame{


    public static final Dimension DIMENSION_PHYSICAL_CARD = new Dimension(300, 419);
    private CardCheckBoxList deckList;
    private CardCheckBoxList sideboardList;
    private CardCheckBoxList draftedCardsList;
    private final Brain brain;
    private NewDraftDeckDialog newDraftDeckDialog;
    private SettingsDialog settingsDialog;
    private EverythingDisablerAndReenabler draftedCardsPanelEnabler;
    private EverythingDisablerAndReenabler sideboardPanelEnabler;
    private final ArrayList<Runnable> deckComponentEnablers = new ArrayList<>();
    private boolean draftEnabled = false;
    private final JFileChooser fc = new JFileChooser();
    private final ArrayList<Container> componentsToBeDisabled = new ArrayList<Container>();
    private final JLabel labelCurrentCardsInDraftingDeck = new JLabel();
    private final JLabel deckCountLabel = new JLabel("deck panel: 0");

    //Dimensions
    public static final Dimension DIMENSION_SCROLL_PANE_DRAFTED_CARDS = new Dimension(300, 400);
    public static final Dimension DIMENSION_DECK_PANEL = new Dimension(350, 500);
    public static final Dimension DIMENSION_SCROLL_PANE_DECK = new Dimension(100, 600);
    public static final Dimension DIMENSION_SCROLL_PANE_SIDEBOARD = new Dimension(300, 400);
    private JLabel previewLabel;
    private JComboBox<String> sortComboBox;

    public Face(Brain brain) {
        super();
        this.brain = brain;
        settingsDialog = new SettingsDialog(brain.getSettingsManager());
    }


    public void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        mainPanel.add(initializeMenuButtonPanel(), gbc);

        gbc.gridy++;
        mainPanel.add(initializeDraftPanel(), gbc);

        gbc.gridx++;
        mainPanel.add(initializePreviewPanel(), gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(initializeSortComboBoxPanel(), gbc);

        add(mainPanel);
        draftedCardsPanelEnabler.disable();
        sideboardPanelEnabler.disable();
        //pack();
        setVisible(true);
    }

    private Component initializeSortComboBoxPanel() {
        JPanel sortComboBoxPanel = new JPanel();
        sortComboBoxPanel.add(new JLabel("Sort by"));
        sortComboBoxPanel.add(initializeSortComboBox());
        return sortComboBoxPanel;
    }

    private Component initializePreviewPanel() {
        JPanel previewPanel = new JPanel();
        previewPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        previewLabel = new JLabel();
        previewLabel.setPreferredSize(DIMENSION_PHYSICAL_CARD);
        previewPanel.add(previewLabel);

        return previewPanel;
    }

    private void previewCardFromDatabase(String id) {
        ImageCrawler crawler = new ImageCrawler(id, previewLabel);
        crawler.execute();
    }



    private Component initializeMenuButtonPanel() {
        JPanel menuButtonPanel = new JPanel();

        menuButtonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        menuButtonPanel.add(initializeNewDraftDeckButton());
        menuButtonPanel.add(initializeLoadButton());
        menuButtonPanel.add(initializeSaveButton());
        menuButtonPanel.add(initializeStartDraftButton());
        menuButtonPanel.add(initializeSettingsButton());

        newDraftDeckDialog = new NewDraftDeckDialog(brain, this::updateAllPanels, this::enableDraftPanel);

        return menuButtonPanel;
    }

    private Component initializeSettingsButton() {
        JButton settingsButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/Categories-preferences-system-icon.png"));
            settingsButton.setIcon(new ImageIcon(img));
            settingsButton.setMargin(new Insets(0, 0, 0, 0));
            settingsButton.setBorder(null);
            settingsButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        settingsButton.addActionListener(getOpenSettingsButtonActionListener());
        return settingsButton;
    }

    private Component initializeStartDraftButton() {
        JButton startDraftButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/Categories-applications-games-icon.png"));
            startDraftButton.setIcon(new ImageIcon(img));
            startDraftButton.setMargin(new Insets(0, 0, 0, 0));
            startDraftButton.setBorder(null);
            startDraftButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startDraftButton.addActionListener(getStartDraftActionListener());
        startDraftButton.setEnabled(false);
        deckComponentEnablers.add(() -> startDraftButton.setEnabled(true));
        return startDraftButton;
    }

    private Component initializeNewDraftDeckButton() {
        JButton newDraftDeckButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/Mimetypes-application-x-zerosize-icon.png"));
            newDraftDeckButton.setIcon(new ImageIcon(img));
            newDraftDeckButton.setMargin(new Insets(0, 0, 0, 0));
            newDraftDeckButton.setBorder(null);
            newDraftDeckButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        newDraftDeckButton.addActionListener(e-> new StartDraftButtonWorker(
                brain,
                this::openNewDraftDeckDialog
        ).execute());
        return newDraftDeckButton;
    }

    private ActionListener getStartDraftActionListener() {
        return (e -> openNewDraftDeckDialog());
    }

    private ActionListener getOpenSettingsButtonActionListener() {
        return (e -> new OpenSettingsButtonWorker(brain, settingsDialog).execute());
    }

    private void openNewDraftDeckDialog() {
        new OpenNewDraftDeckDialogWorker(
                brain,
                newDraftDeckDialog
        ).execute();
    }

    private Component initializeDraftPanel() {
        JPanel draftPanel = new JPanel();
        draftPanel.setLayout(new GridBagLayout());
        draftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        GridBagConstraints gbcDraftPanel = new GridBagConstraints();
        gbcDraftPanel.anchor = GridBagConstraints.NORTH;

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
            list.addCard(new Card("Dunwich", "dun", "Skill", "Rogue", "", false, "ABC", "A card name", "A sbname", null, 2, null));
        }
    }

    public void printCardsToDraftPanel() {
        printCardsToPanel(brain.getDraftedCards(), draftedCardsList);
    }

    public void printCardsToSideboardPanel() {
        printCardsToPanel(brain.getSideboard(), sideboardList);
    }

    private void updateDraftingAndSideboardPanel() {
        printCardsToDraftPanel();
        printCardsToSideboardPanel();
    }

    public void updateDeckPanel() {
        updateDeckCountLabel();
        printCardsToPanel(brain.getDraftedDeck(), deckList);
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
        JPanel draftedCardsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        //draftedCardsPanel.setLayout(new BoxLayout(draftedCardsPanel, BoxLayout.PAGE_AXIS));
        //draftedCardsPanel.setPreferredSize(new Dimension(300,500));

        draftedCardsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftedCardsPanel.add(new JLabel("drafted cards panel"), gbc);
        draftedCardsList = new CardCheckBoxList(this::previewCardFromDatabase);

        gbc.gridy++;

        JScrollPane draftScrollPane = new JScrollPane(draftedCardsList);
        //draftScrollPane.setPreferredSize(DIMENSION_SCROLL_PANE_DRAFTED_CARDS);
        draftedCardsPanel.add(draftScrollPane, gbc);

        gbc.gridy++;

        draftedCardsPanel.add(initializeDraftActionsPanel(), gbc);
        draftedCardsPanelEnabler = new EverythingDisablerAndReenabler(draftedCardsPanel, null);
        return draftedCardsPanel;
    }

    private Component initializeDraftActionsPanel() {
        JPanel draftButtonPanel = new JPanel();
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3,1,100,1);
        draftButtonPanel.add(initializeDraftCardsButton(spinnerModel));
        draftButtonPanel.add(new JSpinner(spinnerModel));
        draftButtonPanel.add(labelCurrentCardsInDraftingDeck);
        resetLabelCurrentCardsInDraftingDeck();


        JPanel otherButtonsPanel = new JPanel();
        otherButtonsPanel.add(initializeRedraftButton());
        otherButtonsPanel.add(initializeAddButton());
        otherButtonsPanel.add(initializeAddSideboardButton());

        JPanel sortButtonPanel = new JPanel();
        sortButtonPanel.add(initializeSortDraftedCardsButton());

        JPanel draftActionsPanel = new JPanel();
        draftActionsPanel.setLayout(new BoxLayout(draftActionsPanel, BoxLayout.Y_AXIS));
        draftButtonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        otherButtonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        draftActionsPanel.add(draftButtonPanel);
        draftActionsPanel.add(otherButtonsPanel);
        draftActionsPanel.add(sortButtonPanel);

        return draftActionsPanel;
    }

    private Component initializeSortDraftedCardsButton() {
        JButton sortDraftedCardsButton = new JButton("Sort Drafted Cards");

        sortDraftedCardsButton.addActionListener(e -> new SortDraftedCardsButtonWorker(
                brain,
                sortComboBox,
                this::updateDraftingAndSideboardPanel
        ).execute());
        return sortDraftedCardsButton;
    }

    private void resetLabelCurrentCardsInDraftingDeck() {
        labelCurrentCardsInDraftingDeck.setText("No cards yet.");
    }

    private void updateLabelCurrentCardsInDraftingDeck() {
        labelCurrentCardsInDraftingDeck.setText(String.format("%d cards left.", brain.getNumberOfCardsInDraftingDeck()));
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
                        this::updateDeckPanel
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
                this::printCardsToDraftPanel,
                this::updateLabelCurrentCardsInDraftingDeck
            ).execute()
        );
        return draftCardsButton;
    }

    private Component initializeDeckPanel(){
        JPanel deckPanel = new JPanel();
        //deckPanel.setPreferredSize(DIMENSION_DECK_PANEL);
        BoxLayout mgr = new BoxLayout(deckPanel, BoxLayout.PAGE_AXIS);
        deckPanel.setLayout(mgr);
        deckPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        deckPanel.add(deckCountLabel);
        deckList = new CardCheckBoxList(this::previewCardFromDatabase);
        JScrollPane deckScrollPane = new JScrollPane(deckList);
        //deckScrollPane.setPreferredSize(DIMENSION_SCROLL_PANE_DECK);
        deckPanel.add(deckScrollPane);

        JPanel deckButtonPanel = new JPanel();
        deckButtonPanel.add(initializeRemoveFromDeckButton());
        deckButtonPanel.add(initializeSortDeckButton());

        deckPanel.add(deckButtonPanel);

        return deckPanel;
    }

    private Component initializeSortComboBox() {
        DefaultComboBoxModel<String> sortComboBoxModel = initializeSortComboBoxModel();
        sortComboBox = new JComboBox<>(sortComboBoxModel);
        return sortComboBox;
    }

    private Component initializeRemoveFromDeckButton() {
        JButton removeFromDeckButton = new JButton("Remove");
        removeFromDeckButton.setEnabled(false);
        deckComponentEnablers.add(()->removeFromDeckButton.setEnabled(true));
        removeFromDeckButton.addActionListener(e -> new RemoveCardFromDeckButtonWorker(
                brain,
                this::updateAllPanels,
                deckList
        ).execute());
        return removeFromDeckButton;
    }

    private Component initializeLoadButton() {
        JButton loadButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/actions-document-open-folder-icon.png"));
            loadButton.setIcon(new ImageIcon(img));
            loadButton.setMargin(new Insets(0, 0, 0, 0));
            loadButton.setBorder(null);
            loadButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadButton.addActionListener(e -> {
            File directory = new File("./data/decks");
            if (!directory.exists()) {
                directory.mkdir();
            }
            fc.setCurrentDirectory(directory);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                }

                @Override
                public String getDescription() {
                    return "Text Files (*.txt)";
                }
            });
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                new LoadDeckButtonWorker(
                        brain,
                        file,
                        this::updateDeckPanel,
                        this::enableDeckComponents,
                        this::updateDraftingAndSideboardPanel
                ).execute();
            }
        });
        return loadButton;
    }

    private void enableDeckComponents() {
        for (Runnable e : deckComponentEnablers) {
            e.run();
        }
        deckComponentEnablers.clear();
    }


    private Component initializeSaveButton() {
        JButton saveButton = new JButton();
        saveButton.setEnabled(false);
        deckComponentEnablers.add(() -> saveButton.setEnabled(true));
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/actions-document-save-icon.png"));
            saveButton.setIcon(new ImageIcon(img));
            saveButton.setMargin(new Insets(0, 0, 0, 0));
            saveButton.setBorder(null);
            saveButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveButton.addActionListener(e -> {
            File directory = new File("./data/decks");
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
                }
                new SaveDeckButtonWorker(brain, file).execute();
            }
        });
        return saveButton;
    }

    private DefaultComboBoxModel<String> initializeSortComboBoxModel() {
        DefaultComboBoxModel<String> sortComboBoxModel = new DefaultComboBoxModel<>();
        sortComboBoxModel.addElement("Type, then name");
        sortComboBoxModel.addElement("Just name");
        sortComboBoxModel.addElement("Faction, then name");
        sortComboBoxModel.addElement("Faction, then XP, then name");
        sortComboBoxModel.addElement("XP, then name");
        sortComboBoxModel.addElement("Cost, then name");
        sortComboBoxModel.setSelectedItem("Faction, then XP, then name");
        return sortComboBoxModel;
    }

    private Component initializeSortDeckButton() {
        JButton sortDeckButton = new JButton("Sort Deck");
        sortDeckButton.setEnabled(false);
        deckComponentEnablers.add(()->sortDeckButton.setEnabled(true));
        sortDeckButton.addActionListener(e -> new SortDeckButtonWorker(
                brain,
                sortComboBox,
                this::updateDeckPanel
        ).execute());
        return sortDeckButton;
    }

    private Component initializeSideBoardPanel() {
        JPanel sideboardPanel = new JPanel();
        //sideboardPanel.setPreferredSize(new Dimension(250,500));
        sideboardPanel.setLayout(new BoxLayout(sideboardPanel, BoxLayout.PAGE_AXIS));
        sideboardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        sideboardPanel.add(new JLabel("sideboard panel"));
        sideboardList = new CardCheckBoxList(this::previewCardFromDatabase);
        JScrollPane sideboardScrollPane = new JScrollPane(sideboardList);
        //sideboardScrollPane.setPreferredSize(DIMENSION_SCROLL_PANE_SIDEBOARD);
        sideboardPanel.add(sideboardScrollPane);
        sideboardPanel.add(initializeSideboardButtonPanel());
        sideboardPanelEnabler = new EverythingDisablerAndReenabler(sideboardPanel, null);
        return sideboardPanel;
    }

    private JPanel initializeSideboardButtonPanel() {
        JPanel sideboardButtonPanel = new JPanel();
        sideboardButtonPanel.add(initializeAddFromSideBoardButton());
        sideboardButtonPanel.add(initializeDiscardFromSideBoardButton());
        return sideboardButtonPanel;
    }

    private Component initializeDiscardFromSideBoardButton() {
        JButton removeFromSideBoardButton = new JButton("Discard");
        removeFromSideBoardButton.addActionListener(e ->
                new DiscardFromSideBoardButtonWorker(
                        brain,
                        sideboardList,
                        this::printCardsToSideboardPanel
                ).execute());
        return removeFromSideBoardButton;
    }

    private Component initializeAddFromSideBoardButton() {
        JButton addFromSideBoardButton = new JButton("Add");
        addFromSideBoardButton.addActionListener(e ->
                new AddFromSideBoardButtonWorker(
                        brain,
                        sideboardList,
                        this::updateAllPanels
                ).execute());
        return addFromSideBoardButton;
    }

    private void updateAllPanels() {
        updateDraftingAndSideboardPanel();
        updateDeckPanel();
        updateLabelCurrentCardsInDraftingDeck();
    }

    private void updateDeckCountLabel() {
        deckCountLabel.setText(String.format("deck panel: %d", brain.getNumberOfCardsInDeck()));
    }

    public void enableDraftPanel() {
        if (!draftEnabled) {
            draftEnabled = true;
            enableDraftPanel(true);
        }
    }

    private void enableDraftPanel(boolean b) {
        if (b) {
            draftedCardsPanelEnabler.reenable();
            sideboardPanelEnabler.reenable();
            enableDeckComponents();
        } else {
            draftedCardsPanelEnabler.disable();
            sideboardPanelEnabler.disable();
        }
    }


}
