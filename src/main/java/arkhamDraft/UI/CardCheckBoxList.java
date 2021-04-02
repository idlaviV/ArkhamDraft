package arkhamDraft.UI;

import arkhamDraft.Card;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CardCheckBoxList {

    private CardCheckBoxListModel model = new CardCheckBoxListModel();
    private final JTable table = new JTable();
    private final Consumer<String> previewCardFromDatabase;

    public CardCheckBoxList(Consumer<String> previewCardFromDatabase) {
        this.previewCardFromDatabase = previewCardFromDatabase;
        table.setModel(model);
        model.addColumn("\u2713");
        model.addColumn("Cards");
        table.getColumnModel().getColumn(0).setMaxWidth(new JCheckBox().getPreferredSize().width);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    public void addCard(Card card) {
        model.addRow(new Object[]{false, card});
    }

    public ArrayList<Card> getCheckedCards() {
        ArrayList<Card> checkedCards = new ArrayList<>();
        for (int i = 0; i<table.getRowCount(); i++) {
            if ((boolean) table.getValueAt(i, 0)) {
                checkedCards.add((Card) table.getValueAt(i,1));
            }
        }
        return checkedCards;
    }

    public void clearList() {
        model.setRowCount(0);
    }

    public void toggleCard(Card card) {

    }

    public JTable getTable() {
        return table;
    }
}
