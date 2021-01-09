package arkhamDraft;

import arkhamDraft.workerPool.AddFilterButtonWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FilterCardsDialog extends JDialog {
    private Brain brain;
    private DeposableFilterList filterList;
    private JComboBox<String> attributeSelector;
    private JComboBox<String> relatorSelector;
    private HintTextField valueSelector;
    private JButton addFilterButton;
    private JLabel currentCardsFilteredLabel;

    public FilterCardsDialog(Brain brain) {
        super();
        this.brain = brain;
        setTitle("Add Cards");
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

        JButton addCardsButton = new JButton("Add cards to draft deck");
        add(addCardsButton);
        addCardsButton.addActionListener(e-> EventQueue.invokeLater(()->{
            brain.guiLeavesFilterCardsDialog();
            valueSelector.resetToHint();
            this.dispose();
        }));

        currentCardsFilteredLabel = new JLabel("Picked up all cards.");
        add(currentCardsFilteredLabel);
    }

    private Component initializeAddFilterButton() {
        addFilterButton = new JButton("Add Filter");
        addFilterButton.addActionListener(e -> {});
        addFilterButton.setEnabled(false);
        addFilterButton.addActionListener(e -> new AddFilterButtonWorker(
                brain,
                attributeSelector,
                relatorSelector,
                valueSelector,
                this::updateFilterListFromBrain,
                this::updateCurrentCardsFiltered
        ).execute());
        return addFilterButton;
    }

    private Component initializeFilterList() {
        filterList = new DeposableFilterList(this);
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

        valueSelector = new HintTextField("Value", 15);
        valueSelector.setEnabled(false);
        filterAdditionPanel.add(valueSelector);

        return filterAdditionPanel;
    }

    private ActionListener attributeSelectorActionListener() {
        return e -> {
            if (attributeSelector.getSelectedItem().equals("Select attribute")) {
                relatorSelector.setEnabled(false);
                valueSelector.setEnabled(false);
                addFilterButton.setEnabled(false);
            } else {
                valueSelector.setEnabled(false);
                initializeRelatorSelector(attributeSelector.getSelectedItem().equals("XP"));
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
                addFilterButton.setEnabled(false);
            } else {
                valueSelector.setEnabled(true);
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
        brain.removeCardFilterFromList(cardFilter);
        revalidate();
        repaint();
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
