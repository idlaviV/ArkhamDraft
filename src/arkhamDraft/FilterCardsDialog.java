package arkhamDraft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class FilterCardsDialog extends JDialog {
    private Brain brain;
    //private DefaultListModel<CardFilter> filterListModel;
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
        addFilterButton = new JButton("Add Filter");
        addFilterButton.addActionListener(e -> {});
        addFilterButton.setEnabled(false);
        add(addFilterButton);
        add(initializeFilterMenu());
        addFilterButton.addActionListener(addFilterButtonAL);

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

    private Component initializeFilterList() {
        filterList = new DeposableFilterList();
        //filterListModel = new DefaultListModel<>();
        //JList<CardFilter> filterList = new JList<>(filterListModel);
        //filterListModel.addElement(new CardFilter((card)->true, "null"));
        //return new JScrollPane(filterList.getComponent());
        return filterList.getComponent();
    }

    private Component initializeFilterMenu() {
        JPanel filterAdditionPanel = new JPanel();
        DefaultComboBoxModel<String> attributeSelectorModel = new DefaultComboBoxModel<>();
        attributeSelectorModel.addElement("Select attribute");
        attributeSelectorModel.addElement("XP");
        attributeSelectorModel.addElement("Faction");
        attributeSelectorModel.addElement("Type");
        attributeSelectorModel.addElement("Trait");
        attributeSelectorModel.addElement("Pack");
        attributeSelectorModel.addElement("Text");
        attributeSelector = new JComboBox<>(attributeSelectorModel);
        filterAdditionPanel.add(attributeSelector);
        attributeSelector.addActionListener(attributeSelectorActionListener());

        relatorSelector = new JComboBox<>();
        relatorSelector.setEnabled(false);
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
        if (isNumerical) {
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
                addFilterButton.setEnabled(true);
            }
        });
    }

    private final ActionListener addFilterButtonAL = e -> EventQueue.invokeLater(() -> {
        ArrayList<String> arguments = Decoder.decryptGUIFilter(attributeSelector.getItemAt(attributeSelector.getSelectedIndex()),
                relatorSelector.getItemAt(relatorSelector.getSelectedIndex()),
                valueSelector.getText());
        brain.addFilterFromGUI(arguments);
    });

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
}
