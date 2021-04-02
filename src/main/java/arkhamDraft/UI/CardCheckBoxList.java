package arkhamDraft.UI;

import arkhamDraft.Card;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CardCheckBoxList {

    private CardCheckBoxListModel model = new CardCheckBoxListModel();
    private final JTable table = new JTable();
    private final Consumer<String> previewCardFromDatabase;
    private final JCheckBox superCheckBox = new JCheckBox();

    public CardCheckBoxList(Consumer<String> previewCardFromDatabase) {
        this.previewCardFromDatabase = previewCardFromDatabase;
        table.setModel(model);
        initializeHeader();
        table.getColumnModel().getColumn(0).setMaxWidth(new JCheckBox().getPreferredSize().width);
        table.getColumnModel().getColumn(1).setCellRenderer(new CardRenderer());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        addMouseListenersToTable(previewCardFromDatabase);
    }

    private void initializeHeader() {
        model.addColumn("\u2713");
        model.addColumn("Cards");
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellHeaderRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (column == 0) {
                    return superCheckBox;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
    }

    private void addMouseListenersToTable(Consumer<String> previewCardFromDatabase) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (col == 1) {
                    Card card = ((Card) table.getModel().getValueAt(row, col));
                    previewCardFromDatabase.accept(card.getCode());
                }
            }
        });
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int col = table.columnAtPoint(e.getPoint());
                if (col == 0) {
                    boolean newStatus = !superCheckBox.isSelected();
                    superCheckBox.setSelected(newStatus);
                    setAllElementsSelected(newStatus);
                }
            }
        });
    }

    private void setAllElementsSelected(boolean newStatus) {
        for (int i=0; i<table.getRowCount(); i++) {
            table.getModel().setValueAt(newStatus, i, 0);
        }
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
