package arkhamDraft;

import javax.swing.*;
import java.awt.*;

public class NewDraftDeckDialog extends JDialog {
    private Brain brain;
    private JLabel currentDraftDeck;
    private FilterCardsDialog filterCardsDialog;

    public NewDraftDeckDialog(Brain brain) {
        super();
        this.brain = brain;
        initializeDialogue();
        setVisible(true);
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
        currentDraftDeck = new JLabel("Draft deck is empty.");
        currentDraftDeckPanel.add(currentDraftDeck);

        JButton addCardsButton = new JButton("Add Cards");
        add(addCardsButton);
        addCardsButton.addActionListener(e -> filterCardsDialog = new FilterCardsDialog(brain));

        JButton finalizeDraftDeckButton = new JButton("Finalize");
        add(finalizeDraftDeckButton);
        finalizeDraftDeckButton.addActionListener(e -> dispose());

    }

    public void addFilterToFilterList(CardFilter newCardFilter) {
        filterCardsDialog.addFilterToFilterList(newCardFilter);
    }
}
