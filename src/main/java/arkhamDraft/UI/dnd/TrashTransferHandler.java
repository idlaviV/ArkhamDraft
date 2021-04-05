package arkhamDraft.UI.dnd;

import arkhamDraft.Brain;
import arkhamDraft.Card;
import arkhamDraft.CardPanel;
import arkhamDraft.UI.workerPool.DragAndDropWorker;

import javax.swing.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TrashTransferHandler extends CardTransferHandler {
    private final CardPanel to = CardPanel.TRASH;

    public TrashTransferHandler(Brain brain, Runnable updateAllPanels) {
        super(brain, updateAllPanels);
    }

    public boolean importData(TransferSupport support) {

        // if we can't handle the import, say so
        if (!canImport(support)) {
            return false;
        }


        // fetch the data and bail if this fails

        new DragAndDropWorker(getBrain(), getFrom(), to, getCard(), -1, this::updateAllPanels).execute();



        /*
        Object[] rowData = new Object[]{false, data};
        model.insertRow(row, rowData);
        */



        return true;
    }



    boolean legalOrigin(CardPanel from) {
        switch (from) {
            case DRAFT:
                break;
            case DECK:
            case SIDEBOARD:
                return true;
            default:
        }
        return false;
    }
}
