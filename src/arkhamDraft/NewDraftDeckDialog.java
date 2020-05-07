package arkhamDraft;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class NewDraftDeckDialog extends JDialog {
    private final Brain brain;
    private JTextArea draftDeckLog;
    private JLabel draftDeckSizeLabel;
    private FilterCardsDialog filterCardsDialog;

    public NewDraftDeckDialog(Brain brain) {
        super();
        this.brain = brain;
        initializeDialogue();
        //setVisible(true);
    }

    private void initializeDialogue() {
        setTitle("New Draft Deck");
        setSize(300,300);
        setLocation(100,100);
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel currentDraftDeckPanel = new JPanel();
        currentDraftDeckPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(currentDraftDeckPanel);

        draftDeckSizeLabel = new JLabel("Draft deck is empty");
        currentDraftDeckPanel.add(draftDeckSizeLabel);

        draftDeckLog = new JTextArea("Created empty draft deck.", 5, 20);
        JScrollPane draftDeckLogScrollPane = new JScrollPane(draftDeckLog);
        draftDeckLogScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        currentDraftDeckPanel.add(draftDeckLogScrollPane);
        draftDeckLog.setBackground(this.getBackground());

        JButton addCardsButton = new JButton("Add Cards");
        add(addCardsButton);
        filterCardsDialog = new FilterCardsDialog(brain);
        addCardsButton.addActionListener(e -> {
            filterCardsDialog.tidyUp();
            brain.guiEntersFilterCardsDialog();
            filterCardsDialog.setVisible(true);
        });

        JButton finalizeDraftDeckButton = new JButton("Finalize");
        add(finalizeDraftDeckButton);
        finalizeDraftDeckButton.addActionListener(e -> {
            brain.guiFinalizeDraftDeck();
            dispose();});

        JButton saveButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("icons/actions-document-save-icon.png"));
            saveButton.setIcon(new ImageIcon(img));
            saveButton.setMargin(new Insets(0, 0, 0, 0));
            saveButton.setBorder(null);
            saveButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(saveButton);
        saveButton.addActionListener(e->brain.guiSaveFilterList());
    }

    public void addFilterToFilterList(CardFilter newCardFilter) {
        filterCardsDialog.addFilterToFilterList(newCardFilter);
    }

    public void updateDraftDeckLog(String newLabel) {
        draftDeckLog.setText(String.format("%s\n%s", draftDeckLog.getText(), newLabel));
        repaint();
    }

    public void tidyUp() {
        draftDeckLog.setText("Created empty draft deck.");
        draftDeckSizeLabel.setText("Draft deck is empty.");
    }

    public void updateDraftDeckSize(int newSize) {
        draftDeckSizeLabel.setText(String.format("Draft deck contains %d cards.", newSize));
    }

    public void updateCurrentCardsFiltered(int cardsFilteredByFilterListSize) {
        filterCardsDialog.updateCurrentCardsFiltered(cardsFilteredByFilterListSize);
    }
}
