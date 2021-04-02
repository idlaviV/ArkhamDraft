package arkhamDraft.UI;

import arkhamDraft.Card;

import javax.naming.OperationNotSupportedException;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CardCheckBoxListModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        //all cells false
        return column == 0;
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

}
