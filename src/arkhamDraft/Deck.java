package arkhamDraft;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck  {

    private ArrayList<Card> physicalDeck = new ArrayList<>();

    public Deck() {
        physicalDeck = new ArrayList<>();
    }

    public Deck(ArrayList<Card> physicalDeck) {
        this.physicalDeck = physicalDeck;
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

    public void addCard(List<Card> cards) {
        physicalDeck.addAll(cards);
    }

    public void addCard(Card card) {
        addCard(Collections.singletonList(card));
    }

    public ArrayList<Card> getCards() {
        return physicalDeck;
    }
}
