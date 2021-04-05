package arkhamDraft.UI.dnd;

import arkhamDraft.Brain;
import arkhamDraft.Card;
import arkhamDraft.CardPanel;
import arkhamDraft.UI.CardCheckBoxListModel;
import arkhamDraft.UI.IdentifiableTable;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;


public class CardCheckBoxListTransferHandler extends CardTransferHandler {
    private final IdentifiableTable table;
    private final CardCheckBoxListModel model;

    public CardCheckBoxListTransferHandler(IdentifiableTable table, CardCheckBoxListModel model, Brain brain, Runnable updateAllPanels) {
        super(brain, updateAllPanels);
        this.table = table;
        this.model = model;
        setTo(table.getPanel());
    }

    public int getSourceActions(JComponent comp) {
        return TransferHandler.MOVE;
    }

    /*To be overwritten by inheriting classes*/
    int fetchDropLocation(TransferSupport support) {
        JTable.DropLocation dl = (JTable.DropLocation)support.getDropLocation();

        return dl.getRow();
    }

    /*To be overwritten by inheriting classes*/
    void updateScroll(int row) {
        Rectangle rect = table.getCellRect(row, 0, false);
        table.scrollRectToVisible(rect);
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

