package arkhamDraft;

import javax.swing.*;
import java.awt.*;

public class HintTextField extends JTextField {

    private final String _hint;
    private Graphics g;

    public HintTextField(String hint, int columns) {
        super();
        super.setColumns(columns);
        _hint = hint;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setHintIfEmpty(g);
    }

    public void resetToHint() {
        setText("");
        setHintIfEmpty(g);
    }

    private void setHintIfEmpty(Graphics g) {
        if (getText().length() == 0) {
            int h = getHeight();
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            int c0 = getBackground().getRGB();
            int c1 = getForeground().getRGB();
            int m = 0xfefefefe;
            int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
            g.setColor(new Color(c2, true));
            g.drawString(_hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
            this.g = g;
        }
    }

    protected void setGraphics(Graphics g) {
        this.g = g;
    }
}