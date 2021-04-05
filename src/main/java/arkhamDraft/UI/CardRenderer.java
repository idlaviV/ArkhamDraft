package arkhamDraft.UI;

import arkhamDraft.Card;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CardRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Card card = (Card) value;
        Component renderer = super.getTableCellRendererComponent(table, card.getDraftInfo(false), isSelected, hasFocus, row, column);
        if (isSelected)
            renderer.setBackground( table.getSelectionBackground() );
        else {
            Color backgroundColor = card.getGUIColor();
            renderer.setBackground(backgroundColor);
            if (backgroundColor.equals(Color.BLUE)) {
                renderer.setForeground(Color.WHITE);
            } else {
                renderer.setForeground(Color.BLACK);
            }
        }
        return renderer;
    }
}
