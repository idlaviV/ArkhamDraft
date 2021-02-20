package arkhamDraft.UI;

import arkhamDraft.Brain;
import arkhamDraft.CardFilter;
import arkhamDraft.Decoder;
import arkhamDraft.Faction;
import arkhamDraft.UI.workerPool.AddCardsToDraftDeckButtonWorker;
import arkhamDraft.UI.workerPool.AddFilterButtonWorker;
import arkhamDraft.UI.workerPool.RemoveCardFilterFromListButtonWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


public class FilterCardsDialog extends JDialog {
    private final Brain brain;
    private DisposableFilterList filterList;
    private JComboBox<String> attributeSelector;
    private JComboBox<String> relatorSelector;
    private HintTextField valueSelector;
    private JButton addFilterButton;
    private JLabel currentCardsFilteredLabel;
    private final Function<Boolean, SwingWorker<Integer, Void>> addCards;
    private JPanel valueSelectorPanel;
    private DefaultComboBoxModel<Faction> factionSelectorModel;
    private boolean hasFactionSelected = false;
    private JComboBox<Faction> factionSelectorBox;

    public FilterCardsDialog(Brain brain, Function<Boolean, SwingWorker<Integer, Void>> addCards) {
        super();
        this.brain = brain;
        this.addCards = addCards;
        setTitle("Add Cards");
        setSize(500,300);
        setSize(500,300);
        setLocation(200,200);
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        initializeDialog();
        //setVisible(true);
    }

    private void initializeDialog() {
        add(new JLabel("Current Filters:"));
        add(initializeFilterList());
        add(initializeAddFilterButton());
        add(initializeFilterMenu());
        add(initializeAddCardsToDraftDeckButton());
        currentCardsFilteredLabel = new JLabel("Picked up all cards.");
        add(currentCardsFilteredLabel);
    }

    private Component initializeAddCardsToDraftDeckButton() {
        JButton addCardsToDraftDeckButton = new JButton("Add cards to draft deck");
        addCardsToDraftDeckButton.addActionListener(e -> {
            new AddCardsToDraftDeckButtonWorker(
                    brain,
                    valueSelector,
                    this::dispose,
                    addCards
            ).execute();
        });
        return addCardsToDraftDeckButton;
    }

    private Component initializeAddFilterButton() {
        addFilterButton = new JButton("Add Filter");
        addFilterButton.addActionListener(e -> {});
        addFilterButton.setEnabled(false);
        addFilterButton.addActionListener(e -> new AddFilterButtonWorker(
                brain,
                this::updateFilterListFromBrain,
                this::updateCurrentCardsFiltered,
                this::addFilterFromUserSelection
        ).execute());
        return addFilterButton;
    }

    private void addFilterFromUserSelection() {
        ArrayList<String> arguments;
        if (hasFactionSelected) {
            arguments = Decoder.decryptGUIFilter(attributeSelector.getItemAt(attributeSelector.getSelectedIndex()),
                    relatorSelector.getItemAt(relatorSelector.getSelectedIndex()),
                    factionSelectorModel.getSelectedItem().toString());
        } else {
            arguments = Decoder.decryptGUIFilter(attributeSelector.getItemAt(attributeSelector.getSelectedIndex()),
                    relatorSelector.getItemAt(relatorSelector.getSelectedIndex()),
                    valueSelector.getText());
        }
        brain.addFilterFromGUI(arguments);
    }

    private Component initializeFilterList() {
        filterList = new DisposableFilterList(this);
        //filterListModel = new DefaultListModel<>();
        //JList<CardFilter> filterList = new JList<>(filterListModel);
        //filterListModel.addElement(new CardFilter((card)->true, "null"));
        //return new JScrollPane(filterList.getComponent());
        return filterList.getComponent();
    }

    private Component initializeFilterMenu() {
        List<String> attributes = Arrays.asList("Select attribute", "XP", "Faction", "Type", "Trait", "Pack", "Text");
        JPanel filterAdditionPanel = new JPanel();
        DefaultComboBoxModel<String> attributeSelectorModel = new DefaultComboBoxModel<>();
        for (String attribute : attributes) {
            attributeSelectorModel.addElement(attribute);
        }
        attributeSelector = new JComboBox<>(attributeSelectorModel);
        filterAdditionPanel.add(attributeSelector);
        attributeSelector.addActionListener(attributeSelectorActionListener());

        relatorSelector = new JComboBox<>();
        relatorSelector.setEnabled(false);
        //I think it shows "contains not" since this is longer then "contains"-->better spacing
        relatorSelector.setPrototypeDisplayValue("contains not");
        filterAdditionPanel.add(relatorSelector);


        filterAdditionPanel.add(initializeValueSelectorPanel());

        return filterAdditionPanel;

    }

    private Component initializeValueSelectorPanel() {
        CardLayout valueSelectorCardLayout = new CardLayout();
        valueSelectorPanel = new JPanel(valueSelectorCardLayout);


        valueSelector = new HintTextField("Value", 15);
        valueSelector.setEnabled(false);
        valueSelectorPanel.add(valueSelector);

        valueSelectorPanel.add(initializeFactionSelectorComboBox());

        return valueSelectorPanel;
    }

    private Component initializeFactionSelectorComboBox() {
        factionSelectorModel = new DefaultComboBoxModel<>();
        factionSelectorBox = new JComboBox<>(factionSelectorModel);
        factionSelectorBox.setEditable(true);
        for (Faction f : Faction.values()) {
            factionSelectorModel.addElement(f);
        }
        factionSelectorBox.setEnabled(false);
        return factionSelectorBox;
    }

    private void switchToDefaultValueSelector() {
        ((CardLayout) valueSelectorPanel.getLayout()).first(valueSelectorPanel);
        hasFactionSelected = false;
    }

    private void switchToFactionValueSelector() {
        ((CardLayout) valueSelectorPanel.getLayout()).last(valueSelectorPanel);
        hasFactionSelected = true;
    }


    private ActionListener attributeSelectorActionListener() {
        return e -> {
            if (attributeSelector.getSelectedItem().equals("Select attribute")) {
                relatorSelector.setEnabled(false);
                valueSelector.setEnabled(false);
                factionSelectorBox.setEnabled(false);
                addFilterButton.setEnabled(false);
            } else {
                valueSelector.setEnabled(false);
                factionSelectorBox.setEnabled(false);
                initializeRelatorSelector(attributeSelector.getSelectedItem().equals("XP"));
            }
            if (attributeSelector.getSelectedItem().equals("Faction")) {
                switchToFactionValueSelector();
            } else {
                switchToDefaultValueSelector();
            }
        };
    }

    private void initializeRelatorSelector(Boolean isNumerical) {
        relatorSelector.setEnabled(true);
        DefaultComboBoxModel<String> comboBoxModel= new DefaultComboBoxModel<>();
        comboBoxModel.addElement("Choose relator");
        if (isNumerical) {//TODO: Use a list
            comboBoxModel.addElement("=");
            comboBoxModel.addElement(">");
            comboBoxModel.addElement("<");
            comboBoxModel.addElement(">=");
            comboBoxModel.addElement("<=");
        } else {
            comboBoxModel.addElement("contains");
            comboBoxModel.addElement("contains not");
        }
        relatorSelector.setModel(comboBoxModel);
        relatorSelector.addActionListener(e -> {
            if (relatorSelector.getSelectedItem().equals("Choose relator")) {
                valueSelector.setEnabled(false);
                factionSelectorBox.setEnabled(false);
                addFilterButton.setEnabled(false);
            } else {
                valueSelector.setEnabled(true);
                factionSelectorBox.setEnabled(true);
                //TODO: Valueselector should have a formatter if (isNumerical)
                addFilterButton.setEnabled(true);
            }
        });
    }



    public void addFilterToFilterList(CardFilter newCardFilter) {
            filterList.addCardFilter(newCardFilter);
            revalidate();
    }

    public void tidyUp() {
        filterList.clearList();
        currentCardsFilteredLabel.setText("Picked up all cards.");
    }

    public void updateCurrentCardsFiltered(int cardsFilteredByFilterListSize) {
        currentCardsFilteredLabel.setText(String.format("%d cards left.", cardsFilteredByFilterListSize));
    }

    public void removeCardFilterFromList(CardFilter cardFilter) {
        new RemoveCardFilterFromListButtonWorker(brain,
                cardFilter,
                this::revalidate,
                this::repaint,
                this::updateCurrentCardsFiltered
        ).execute();
    }

    public void updateFilterListFromBrain() {
        setFilterList(brain.getFilterList());
    }

    private void setFilterList(ArrayList<CardFilter> newFilterList) {
        filterList.clearList();
        for (CardFilter filter:newFilterList) {
            filterList.addCardFilter(filter);
        }
        revalidate();
        repaint();
    }
}
