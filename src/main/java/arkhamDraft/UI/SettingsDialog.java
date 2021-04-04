package arkhamDraft.UI;

import arkhamDraft.Cycle;
import arkhamDraft.Pack;
import arkhamDraft.SettingsManager;
import arkhamDraft.UI.workerPool.ApplySettingsWorker;
import arkhamDraft.UI.workerPool.GenerateBlacklistWorker;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class SettingsDialog extends JDialog {
    private static final Dimension DIMENSION_SCROLL_PANE_PACK_SELECTOR_TREE = new Dimension(300, 300);
    private final SettingsManager settingsManager;
    private JCheckBox regularCardsCheckBox;
    private JButton applyButton;
    private JCheckBoxTree packSelectorCheckBoxTree;
    private final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Packs");
    private JPanel packSelectorPanel;
    private JCheckBox secondCoreCheckBox;


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
        setSize(600,500);
        setLocation(50,50);
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(true);
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
        mainPanel.add(initializeSecondCorePanel(), gbc);
        gbc.gridy++;
        mainPanel.add(initializeGenerateBlacklistButton(), gbc);
        gbc.gridy++;

        mainPanel.add(initializeCloseButtonsPanel(), gbc);
        add(mainPanel);
        pack();
    }

    private Component initializeGenerateBlacklistButton() {
        JPanel generateBlacklistPanel = new JPanel();
        JButton generateBlacklistButton = new JButton("Generate Default Blacklist");
        generateBlacklistButton.addActionListener(e -> new GenerateBlacklistWorker(settingsManager).execute());

        generateBlacklistPanel.add(generateBlacklistButton);
        return generateBlacklistPanel;
    }

    private Component initializeSecondCorePanel() {
        JPanel secondCorePanel = new JPanel();
        secondCorePanel.setBorder(BorderFactory.createCompoundBorder());

        secondCoreCheckBox = new JCheckBox("Second Core");
        secondCoreCheckBox.addActionListener(e -> changesWereMaid(true));
        secondCorePanel.add(secondCoreCheckBox);
        return secondCorePanel;
    }

    private Component initializePackSelectorPanel() {
        packSelectorPanel = new JPanel();
        packSelectorPanel.setBorder(BorderFactory.createCompoundBorder());
        //packSelectorPanel.setPreferredSize(new Dimension(300,300));

        buildPackSelectorScrollPane();
        return packSelectorPanel;
    }

    private void buildPackSelectorScrollPane() {
        packSelectorCheckBoxTree = new JCheckBoxTree();
        //packSelectorCheckBoxTree.setPreferredSize(new Dimension(200, 200));
        packSelectorCheckBoxTree.addCheckChangeEventListener(e -> SwingUtilities.invokeLater(() -> changesWereMaid(true)));

        JScrollPane packSelectorScrollPane = new JScrollPane(packSelectorCheckBoxTree);
        packSelectorScrollPane.setPreferredSize(DIMENSION_SCROLL_PANE_PACK_SELECTOR_TREE);
        packSelectorPanel.add(packSelectorScrollPane);
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
        new ApplySettingsWorker(settingsManager, regularCardsCheckBox.isSelected(), secondCoreCheckBox.isSelected(), packSelectorCheckBoxTree.getCheckedPaths(), this::changesWereMaid).execute();
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
        secondCoreCheckBox.setSelected(settingsManager.getSecondCore());
        populatePackTree();
    }

    private void populatePackTree() {
        List<Pack> ownedPacks = settingsManager.getOwnedPacks();
        top.removeAllChildren();
        List<TreePath> checkedPaths = new ArrayList<>();

        List<Cycle> cycles = settingsManager.getCycles();

        for (Cycle cycle : cycles) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(cycle);
            top.add(child);
            List<Pack> packs = cycle.getPacks();
            for (Pack pack : packs) {
                DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(pack);
                child.add(leaf);
                if (ownedPacks.contains(pack)) {
                    TreePath tp = new TreePath(leaf.getPath());
                    checkedPaths.add(tp);
                }
            }
            if (ownedPacks.containsAll(cycle.getPacks())) {
                checkedPaths.add(new TreePath(child.getPath()));
            }
        }
        if (ownedPacks.containsAll(settingsManager.getPacks())) {
            checkedPaths.add(new TreePath(top.getPath()));
        }

        DefaultTreeModel model = new DefaultTreeModel(top);
        packSelectorCheckBoxTree.setModel(model);
        checkPathsInPackSelectorTree(checkedPaths);

        packSelectorCheckBoxTree.repaint();
    }

    private void checkPathsInPackSelectorTree(List<TreePath> checkedPaths) {
        for (TreePath tp : checkedPaths) {
            packSelectorCheckBoxTree.checkSubTree(tp, true);
        }
    }

    private void changesWereMaid(boolean flag) {
        applyButton.setEnabled(flag);
    }
}
