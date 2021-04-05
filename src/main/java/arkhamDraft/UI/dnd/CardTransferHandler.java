package arkhamDraft.UI.dnd;

import arkhamDraft.Brain;
import arkhamDraft.Card;
import arkhamDraft.CardPanel;
import arkhamDraft.UI.workerPool.DragAndDropWorker;

import javax.swing.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class CardTransferHandler extends TransferHandler {

    private final Brain brain;
    private final Runnable updateAllPanels;
    private Card card;
    private CardPanel from;
    private CardPanel to;

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
        return true;
    }

    public boolean importData(TransferSupport support) {

        // if we can't handle the import, say so
        if (!canImport(support)) {
            return false;
        }

        int row = fetchDropLocation(support);

        // fetch the data and bail if this fails
        new DragAndDropWorker(getBrain(), getFrom(), to, getCard(), row, this::updateAllPanels).execute();

        updateScroll(row);

        return true;
    }

    /*To be overwritten by inheriting classes*/
    int fetchDropLocation(TransferSupport support) {
        return -1;
    }

    /*To be overwritten by inheriting classes*/
    void updateScroll(int row) {
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

    void setTo(CardPanel to) {
        this.to = to;
    }

    void updateAllPanels() {
        updateAllPanels.run();
    }
}
