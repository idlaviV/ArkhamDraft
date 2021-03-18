package arkhamDraft.UI;

import arkhamDraft.SettingsManager;
import arkhamDraft.UI.workerPool.applySettingsButtonWorker;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {
    private final SettingsManager settingsManager;
    private JCheckBox regularCardsCheckBox;
    private JButton applyButton;


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
        setSize(300,300);
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
        mainPanel.add(initializeCloseButtonsPanel(), gbc);
        add(mainPanel);
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
    }

    private void changesWereMaid(boolean flag) {
        applyButton.setEnabled(flag);
    }
}
