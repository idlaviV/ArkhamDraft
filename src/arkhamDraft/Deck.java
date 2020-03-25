package arkhamDraft;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Deck  {

    private ArrayList<Card> physicalDeck = new ArrayList<>();

    public Deck() {
        physicalDeck = new ArrayList<>();
    }

    public Deck(File fileDeck, CardBox masterCardBox) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileDeck));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.equals("") && Character.isDigit(line.charAt(0))) {
                physicalDeck.addAll(masterCardBox.findCardFromString(line));
            }
        }
    }

    public List<Card> getCards() {
        return physicalDeck;
    }
}
