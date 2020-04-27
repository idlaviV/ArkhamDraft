package arkhamDraft;


import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardCheckBoxList extends JList<Card>
{
    protected static Border noFocusBorder =
            new EmptyBorder(1, 1, 1, 1);
    private Map<Card, Boolean> jBoxStatus = new HashMap<>();
    private final DefaultListModel<Card> listModel = new DefaultListModel<>();

    public CardCheckBoxList()
    {
        setCellRenderer(new CellRenderer());
        addMouseListener(new MouseAdapter()
                         {
                             public void mousePressed(MouseEvent e)
                             {
                                 int index = locationToIndex(e.getPoint());

                                 if (index != -1) {
                                     toggleCard(getModel().getElementAt(index));
                                     repaint();
                                 }
                             }
                         }
        );

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setModel(listModel);
    }

    public void addCard(Card card) {
/*        ListModel<Card> currentList = this.getModel();
        Card[] newList = new Card[currentList.getSize() + 1];
        for (int i = 0; i < currentList.getSize(); i++) {
            newList[i] = currentList.getElementAt(i);
        }
        newList[newList.length - 1] = card;
        jBoxStatus.put(card, false);
        setListData(newList);*/
        jBoxStatus.put(card, false);
        listModel.addElement(card);
        //repaint();
    }

    public ArrayList<Card> getCheckedCards() {
        ArrayList<Card> checkedCards = new ArrayList<>();
        for (int index = 0; index < listModel.getSize(); index++) {
            Card currentCard = listModel.get(index);
            if (jBoxStatus.get(currentCard)) {
                checkedCards.add(currentCard);
            }
        }
        return checkedCards;
    }

    public void clearList() {
        //setListData(new Card[0]);
        listModel.removeAllElements();
        jBoxStatus = new HashMap<>();
    }

    public void toggleCard(Card card) {
        jBoxStatus.put(card, !jBoxStatus.get(card));
    }

    protected class CellRenderer implements ListCellRenderer<Card>
    {
        public Component getListCellRendererComponent(
                JList<? extends Card> list, Card value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JCheckBox checkbox = new JCheckBox(value.getDraftInfo(false), jBoxStatus.get(value));
            checkbox.setBackground(isSelected ?
                    getSelectionBackground() : getBackground());
            checkbox.setForeground(isSelected ?
                    getSelectionForeground() : getForeground());
            checkbox.setEnabled(isEnabled());
            checkbox.setFont(getFont());
            checkbox.setFocusPainted(false);
            checkbox.setBorderPainted(true);
            checkbox.setBorder(isSelected ?
                    UIManager.getBorder(
                            "List.focusCellHighlightBorder") : noFocusBorder);
            return checkbox;
        }


    }
}