package arkhamDraft.UI;

import javax.swing.*;
import java.awt.*;

public class SavePromptDialog extends JDialog {

    public SavePromptDialog() {
        super();
        setTitle("Unsaved Changes");
        setLocation(200,200);
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        initializeDialog();
        pack();
    }

    private void initializeDialog() {
        add(initializeTextField());
        add(initializeButtonPanel());
    }

    private Component initializeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        buttonPanel.add(initializeYesButton());
        buttonPanel.add(initializeNoButton());
        buttonPanel.add(initializeCancelButton());
        return buttonPanel;
    }

    private Component initializeCancelButton() {
        JButton cancelButton = new JButton("Cancel");
        return cancelButton;
    }

    private Component initializeNoButton() {
        JButton noButton = new JButton("No");
        return noButton;
    }

    private Component initializeYesButton() {
        JButton yesButton = new JButton("Yes");
        return yesButton;
    }

    private Component initializeTextField() {
        JLabel text1 = new JLabel("Your deck has unsaved changes.");
        JLabel text2 = new JLabel("Do you want to save it before continuing?");
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        textPanel.add(text1);
        textPanel.add(text2);
        return textPanel;
    }
}
