package arkhamDraft.UI;

import arkhamDraft.Card;

import javax.swing.*;
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
        model.setColumnCount(2);
    }

    public void addCard(Card card) {
        model.addRow(new Object[]{new JCheckBox(), card});
    }

    public ArrayList<Card> getCheckedCards() {
        return null;
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
