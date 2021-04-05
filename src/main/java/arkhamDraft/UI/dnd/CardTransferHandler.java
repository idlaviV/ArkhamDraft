package arkhamDraft.UI.dnd;

import arkhamDraft.Brain;
import arkhamDraft.Card;
import arkhamDraft.CardPanel;

import javax.swing.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class CardTransferHandler extends TransferHandler {

    private final Brain brain;
    private final Runnable updateAllPanels;
    private Card card;
    private CardPanel from;

    public CardTransferHandler(Brain brain, Runnable updateAllPanels) {
        this.brain = brain;
        this.updateAllPanels = updateAllPanels;
    }

    public boolean canImport(TransferSupport support) {
        // we only support drops (not clipboard paste)
        if (!support.isDrop()) {
            return false;
        }

        extractCardAndFromFromSupport(support);
        if (!legalOrigin(from)) {
            return false;
        }

        /*
        // we only import Strings
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }*/

        return true;
    }

    /*To be overwritten by inheriting classes*/
    boolean legalOrigin(CardPanel from) {
        return false;
    }

    void extractCardAndFromFromSupport(TransferSupport support) {
        Object[] transferData;
        try {
            transferData = (Object[]) support.getTransferable().getTransferData(CardTransferable.cardFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        card = (Card) transferData[0];
        from = (CardPanel) transferData[1];
    }

    Card getCard() {
        return card;
    }

    CardPanel getFrom() {
        return from;
    }

    Brain getBrain() {
        return brain;
    }

    void updateAllPanels() {
        updateAllPanels.run();
    }
}
