package arkhamDraft.UI.dnd;

import arkhamDraft.Card;
import arkhamDraft.UI.CardCheckBoxListModel;
import arkhamDraft.UI.dnd.CardTransferable;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;


public class CardCheckBoxListTransferHandler extends TransferHandler {
    private final JTable table;
    private final CardCheckBoxListModel model;

    public CardCheckBoxListTransferHandler(JTable table, CardCheckBoxListModel model) {
        super();
        this.table = table;
        this.model = model;
    }

    public int getSourceActions(JComponent comp) {
        return TransferHandler.MOVE;
    }

    public boolean canImport(TransferSupport support) {
        // we only support drops (not clipboard paste)
        if (!support.isDrop()) {
            return false;
        }

        /*
        // we only import Strings
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }*/

        return true;
    }

    public boolean importData(TransferSupport support) {
        // if we can't handle the import, say so
        if (!canImport(support)) {
            return false;
        }

        // fetch the drop location
        JTable.DropLocation dl = (JTable.DropLocation)support.getDropLocation();

        int row = dl.getRow();

        // fetch the data and bail if this fails
        Card data;
        try {
            data = (Card)support.getTransferable().getTransferData(CardTransferable.cardFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            return false;
        }

        Object[] rowData = new Object[]{false, data};
        model.insertRow(row, rowData);

        Rectangle rect = table.getCellRect(row, 0, false);
        table.scrollRectToVisible(rect);


        return true;
    }

    private int index = 0;

    public Transferable createTransferable(JComponent comp) {
        index = table.getSelectedRow();
        if (index < 0 || index >= model.getRowCount()) {
            return null;
        }

        CardTransferable transferable = new CardTransferable((Card) model.getValueAt(index, 1));
        return transferable;
    }

    public void exportDone(JComponent comp, Transferable trans, int action) {
        if (action != MOVE) {
            return;
        }

        model.removeRow(index);
    }
}

