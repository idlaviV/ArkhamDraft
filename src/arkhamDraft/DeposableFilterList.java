package arkhamDraft;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DeposableFilterList {

    private final ArrayList<CardFilter> filters;
    private final DefaultTableModel tableModel;
    private final JTable guiList;
    private JScrollPane returnValue;
    private final FilterCardsDialog parentDialog;

    public DeposableFilterList(FilterCardsDialog parentDialog){
        this.parentDialog = parentDialog;
        filters = new ArrayList<>();
        tableModel = new DefaultTableModel();
        tableModel.setColumnCount(2);
        guiList = new JTable(tableModel);
        guiList.getColumnModel().getColumn(1).setCellRenderer(new DeposableFilterListRenderer());
        guiList.setLayout(new BoxLayout(guiList, BoxLayout.Y_AXIS));
    }

    public void addCardFilter(CardFilter cardFilter) {
        //guiList.removeAll();
        filters.add(cardFilter);
        tableModel.addRow(new Object[]{cardFilter.toString(), newPanel(cardFilter)});
        //guiList.add(newPanel(cardFilter));

    }

    private void removeCardFilterFromList(CardFilter cardFilter) {
        parentDialog.removeCardFilterFromList(cardFilter);
        int index = filters.indexOf(cardFilter);
        filters.remove(cardFilter);
        tableModel.removeRow(index);
        //for (CardFilter filter : filters) {
        //    guiList.add(newPanel(filter));
        //}
        repaint();
    }

    private JPanel newPanel(CardFilter cardFilter) {
        JPanel newPanel = new JPanel();
        newPanel.add(new JLabel(cardFilter.toString()));
        JButton newButton = new JButton("Remove");
        newButton.addActionListener(e -> {
            removeCardFilterFromList(cardFilter);
        });
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
