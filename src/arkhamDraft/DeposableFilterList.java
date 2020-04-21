package arkhamDraft;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DeposableFilterList {

    private ArrayList<CardFilter> filters;
    private JPanel guiList;
    private JScrollPane returnValue;

    public DeposableFilterList(){
        filters = new ArrayList<>();
        guiList = new JPanel();
        guiList.setLayout(new BoxLayout(guiList, BoxLayout.Y_AXIS));
    }

    public void addCardFilter(CardFilter cardFilter) {
        //guiList.removeAll();
        filters.add(cardFilter);
        JPanel newPanel = new JPanel();
        newPanel.add(new JLabel(cardFilter.toString()));
        JButton newButton = new JButton("Remove");
        newButton.addActionListener(e -> {

        });
        newPanel.add(newButton);
        newPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        guiList.add(newPanel);
        /*for (CardFilter filter : filters) {
            JPanel newPanel = new JPanel();
            newPanel.add(new JLabel(filter.toString()));
            JButton newButton = new JButton("Remove");
            newButton.addActionListener(e -> {

            });
            newPanel.add(newButton);
            newPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            guiList.add(newPanel);
        }*/
    }

    public Component getComponent() {
        returnValue = new JScrollPane(guiList);
        return returnValue;
    }

    public void clearList() {
        guiList.removeAll();
        filters.clear();
    }

    public void repaint() {
        returnValue.revalidate();
        returnValue.repaint();
    }
}
