package arkhamDraft.UI;

import javax.swing.table.DefaultTableModel;

public class CardCheckBoxListModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        //all cells false
        return column == 0;
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

}
