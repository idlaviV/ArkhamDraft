package arkhamDraft.UI.dnd;

import arkhamDraft.Brain;
import arkhamDraft.Card;
import arkhamDraft.CardPanel;
import arkhamDraft.UI.IdentifiableTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Transferable;


public class CardCheckBoxListTransferHandler extends CardTransferHandler {
    private final IdentifiableTable table;
    private int index = 0;

    public CardCheckBoxListTransferHandler(IdentifiableTable table, Brain brain, Runnable updateAllPanels) {
        super(brain, updateAllPanels);
        this.table = table;
        setTo(table.getPanel());
    }

    @Override
    public int getSourceActions(JComponent comp) {
        return TransferHandler.MOVE;
    }

    @Override
    int fetchDropLocation(TransferSupport support) {
        JTable.DropLocation dl = (JTable.DropLocation)support.getDropLocation();

        return dl.getRow();
    }

    @Override
    void updateScroll(int row) {
        Rectangle rect = table.getCellRect(row, 0, false);
        table.scrollRectToVisible(rect);
    }

    @Override
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

    @Override
    public Transferable createTransferable(JComponent comp) {
        index = table.getSelectedRow();
        if (index < 0 || index >= table.getModel().getRowCount()) {
            return null;
        }
        return new CardTransferable((Card) table.getModel().getValueAt(index, 1), table.getPanel());
    }

    @Override
    public void exportDone(JComponent comp, Transferable trans, int action) {
        if (action != MOVE) {
            return;
        }
        ((DefaultTableModel) table.getModel()).removeRow(index);
    }
}
