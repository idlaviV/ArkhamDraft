package arkhamDraft.UI;

import arkhamDraft.CardFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class DisposableFilterList {

    private final ArrayList<CardFilter> filters;
    private final DefaultTableModel tableModel;
    private final JTable guiList;
    private JScrollPane returnValue;
    private final FilterCardsDialog parentDialog;

    public DisposableFilterList(FilterCardsDialog parentDialog){
        this.parentDialog = parentDialog;
        filters = new ArrayList<>();
        tableModel = new DefaultTableModel();
        tableModel.setColumnCount(2);
        guiList = new JTable(tableModel);
        guiList.getColumnModel().getColumn(1).setCellRenderer(new DeposableFilterListRenderer());
        guiList.setTableHeader(null);
        guiList.getColumnModel().getColumn(0).setPreferredWidth(300);
        guiList.getColumnModel().getColumn(1).setPreferredWidth(50);
        guiList.getSelectionModel().addListSelectionListener(e -> {
            if (guiList.getSelectedRow()>-1) {
                if (guiList.getSelectedColumn() == 1) {
                    removeCardFilterFromList(guiList.getSelectedRow());
                }
            }
        });
    }

    public void addCardFilter(CardFilter cardFilter) {
        filters.add(cardFilter);
        tableModel.addRow(new Object[]{cardFilter.toString(), newPanel(cardFilter)});

    }

    private void removeCardFilterFromList(int index) {
        parentDialog.removeCardFilterFromList(filters.get(index));
        filters.remove(index);
        tableModel.removeRow(index);
        repaint();
    }

    private JPanel newPanel(CardFilter cardFilter) {
        JPanel newPanel = new JPanel();
        newPanel.add(new JLabel(cardFilter.toString()));
        JButton newButton = new JButton("Remove");
        newPanel.add(newButton);
        newPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        return newPanel;
    }

    public Component getComponent() {
        returnValue = new JScrollPane(guiList);
        return returnValue;
    }

    public void clearList() {
        tableModel.setRowCount(0);
        filters.clear();
    }

    public void repaint() {
        returnValue.revalidate();
        returnValue.repaint();
    }

    private static class DeposableFilterListRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return new JButton("Remove");
        }
    }


}
