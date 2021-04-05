package arkhamDraft.UI.dnd;

import arkhamDraft.Brain;
import arkhamDraft.Card;
import arkhamDraft.CardPanel;
import arkhamDraft.UI.CardCheckBoxListModel;
import arkhamDraft.UI.IdentifiableTable;
import arkhamDraft.UI.workerPool.DragAndDropWorker;
import sun.awt.datatransfer.TransferableProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;


public class CardCheckBoxListTransferHandler extends CardTransferHandler {
    private final IdentifiableTable table;
    private final CardCheckBoxListModel model;

    public CardCheckBoxListTransferHandler(IdentifiableTable table, CardCheckBoxListModel model, Brain brain, Runnable updateAllPanels) {
        super(brain, updateAllPanels);
        this.table = table;
        this.model = model;
    }

    public int getSourceActions(JComponent comp) {
        return TransferHandler.MOVE;
    }

    public boolean importData(TransferSupport support) {

        // if we can't handle the import, say so
        if (!canImport(support)) {
            return false;
        }

        CardPanel to = table.getPanel();

        // fetch the drop location
        JTable.DropLocation dl = (JTable.DropLocation)support.getDropLocation();

        int row = dl.getRow();

        // fetch the data and bail if this fails

        new DragAndDropWorker(getBrain(), getFrom(), to, getCard(), row, this::updateAllPanels).execute();



        /*
        Object[] rowData = new Object[]{false, data};
        model.insertRow(row, rowData);
        */

        Rectangle rect = table.getCellRect(row, 0, false);
        table.scrollRectToVisible(rect);


        return true;
    }

    boolean legalOrigin(CardPanel from) {
        CardPanel to = table.getPanel();
        switch (from) {
            case DRAFT:
                if (CardPanel.DECK.equals(to)) {
                    return true;
                }
                if (CardPanel.SIDEBOARD.equals(to)) {
                    return true;
                }
                break;
            case DECK:
                break;
            case SIDEBOARD:
                if (CardPanel.DECK.equals(to)) {
                    return true;
                }
                break;
            default:
        }
        return false;
    }

    private int index = 0;

    public Transferable createTransferable(JComponent comp) {
        index = table.getSelectedRow();
        if (index < 0 || index >= model.getRowCount()) {
            return null;
        }

        return new CardTransferable((Card) model.getValueAt(index, 1), table.getPanel());
    }

    public void exportDone(JComponent comp, Transferable trans, int action) {
        if (action != MOVE) {
            return;
        }

        model.removeRow(index);
    }
}

