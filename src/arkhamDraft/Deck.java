package arkhamDraft;

import java.io.*;
import java.util.*;

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

    public ArrayList<String> getPrintInfo() {
        sortDeck();
        ArrayList<String> printInfo = new ArrayList<>();
        if (physicalDeck.size() == 0) {
            return printInfo;
        }
        Iterator<Card> deckIterator = physicalDeck.iterator();
        Card next = null;
        int cardinality = 1;
        do {
            Card current = next;
            next = deckIterator.next();
            if (next.equals(current)) {
                cardinality++;
            } else {
                if (current != null) {
                    printInfo.add(String.format("%dx %s%s%s", cardinality, current.getFactionColor(), current.getDraftInfo(), Face.ANSI_RESET));
                    cardinality = 1;
                }
            }
        } while (deckIterator.hasNext());
        Card current = next;
        printInfo.add(String.format("%dx %s%s%s", cardinality, current.getFactionColor(), current.getDraftInfo(), Face.ANSI_RESET));
        return printInfo;
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

    private void sortDeck() {
        Collections.sort(physicalDeck, Card.typeNameC);
    }
}
