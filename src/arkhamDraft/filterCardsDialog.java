package arkhamDraft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class filterCardsDialog extends JDialog {
    private Brain brain;
    private JList<CardFilter> filterList;
    private JComboBox<String> attributeSelector;
    private JComboBox<String> relatorSelector;
    private JTextField valueSelector;
    private JButton addFilterButton;

    public filterCardsDialog(Brain brain) {
        super();
        this.brain = brain;
        setTitle("Add Cards");
        setSize(500,300);
        setLocation(200,200);
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        initializeDialog();
        setVisible(true);
    }

    private void initializeDialog() {
        add(new JLabel("Current Filters:"));
        add(initializeFilterList());
        addFilterButton = new JButton("Add Filter");
        addFilterButton.addActionListener(e -> {});
        addFilterButton.setEnabled(false);
        add(addFilterButton);
        add(initializeFilterMenu());
    }

    private Component initializeFilterList() {
        DefaultListModel<CardFilter> listModel = new DefaultListModel<>();
        filterList = new JList<>(listModel);
        listModel.addElement(new CardFilter((card)->true, "null"));
        return filterList;
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

        valueSelector = new JTextField("Value", 15);
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
                initializeRelatorSelector(attributeSelector.getSelectedItem().equals("XP"));
            }
        };
    }

    private void initializeRelatorSelector(Boolean isNumerical) {
        relatorSelector.setEnabled(true);
        DefaultComboBoxModel<String> comboBoxModel= new DefaultComboBoxModel<>();
        comboBoxModel.addElement("Choose Relator");
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
    }
}
