package arkhamDraft.UI;

import arkhamDraft.CardPanel;

import javax.swing.*;

public class IdentifiableTable extends JTable {
    private final CardPanel panel;

    public IdentifiableTable(CardPanel panel) {
        super();
        this.panel = panel;
    }

    public CardPanel getPanel() {
        return panel;
    }
}
