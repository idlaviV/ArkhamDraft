package arkhamDraft.UI;

import arkhamDraft.Cycle;
import arkhamDraft.Pack;
import arkhamDraft.SettingsManager;
import arkhamDraft.UI.workerPool.applySettingsButtonWorker;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.List;


public class SettingsDialog extends JDialog {
    private static final Dimension DIMENSION_SCROLL_PANE_PACK_SELECTOR_TREE = new Dimension(300, 300);
    private final SettingsManager settingsManager;
    private JCheckBox regularCardsCheckBox;
    private JButton applyButton;
    private JCheckBoxTree packSelectorCheckBoxTree;


    public SettingsDialog(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
        initializeSettingsDialog();
    }

    public void openDialog() {
        readSettings();
        changesWereMaid(false);
    }

    private void initializeSettingsDialog() {
        setTitle("Settings");
        setSize(500,500);
        setLocation(100,100);
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(1,1,1,1);


        mainPanel.add(new JLabel("Settings"), gbc);
        gbc.gridy++;
        mainPanel.add(initializeRegularCardsPanel(), gbc);
        gbc.gridy++;
        mainPanel.add(initializePackSelectorPanel(), gbc);
        gbc.gridy++;
        mainPanel.add(initializeCloseButtonsPanel(), gbc);
        add(mainPanel);
    }

    private Component initializePackSelectorPanel() {
        JPanel packSelectorPanel = new JPanel();
        packSelectorPanel.setBorder(BorderFactory.createCompoundBorder());
        packSelectorPanel.setPreferredSize(new Dimension(300,300));


        packSelectorCheckBoxTree = new JCheckBoxTree();

        JScrollPane packSelectorScrollPane = new JScrollPane(packSelectorCheckBoxTree);
        packSelectorScrollPane.setPreferredSize(DIMENSION_SCROLL_PANE_PACK_SELECTOR_TREE);

        packSelectorPanel.add(packSelectorScrollPane);
        return packSelectorPanel;
    }

    private Component initializeCloseButtonsPanel() {
        JPanel closeButtonsPanels = new JPanel();

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        applyButton = new JButton("Apply");
        saveButton.addActionListener(e -> {
            applyChanges();
            dispose();
        });

        applyButton.addActionListener(e -> applyChanges());

        cancelButton.addActionListener(e -> dispose());

        closeButtonsPanels.add(saveButton);
        closeButtonsPanels.add(cancelButton);
        closeButtonsPanels.add(applyButton);

        return closeButtonsPanels;
    }

    private void applyChanges() {
        new applySettingsButtonWorker(settingsManager, regularCardsCheckBox.isSelected(), this::changesWereMaid).execute();
    }

    private Component initializeRegularCardsPanel() {
        JPanel regularCardsPanel = new JPanel();

        regularCardsCheckBox = new JCheckBox("Regular Cards");
        regularCardsCheckBox.addActionListener(e -> changesWereMaid(true));
        regularCardsPanel.add(regularCardsCheckBox);

        return regularCardsPanel;
    }

    private void readSettings() {
        regularCardsCheckBox.setSelected(settingsManager.getRegularCardsFlag());
        populatePackTree();
    }

    private void populatePackTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Packs");
        List<Cycle> cycles = settingsManager.getCycles();
        for (Cycle cycle : cycles) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(cycle);
            top.add(child);
            List<Pack> packs = cycle.getPacks();
            for (Pack pack : packs) {
                child.add(new DefaultMutableTreeNode(pack));
            }
        }

        DefaultTreeModel model = new DefaultTreeModel(top);
        packSelectorCheckBoxTree.setModel(model);
    }

    private void changesWereMaid(boolean flag) {
        applyButton.setEnabled(flag);
    }
}
