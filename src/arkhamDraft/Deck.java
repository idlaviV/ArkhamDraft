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

    public ArrayList<String> getPrintInfo2() {
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
                    printInfo.add(String.format("%dx %s", cardinality, current.getDraftInfo()));
                    cardinality = 1;
                }
            }
        } while (deckIterator.hasNext());
        Card current = next;
        printInfo.add(String.format("%dx %s", cardinality, current.getDraftInfo()));
        return printInfo;
    }

    public ArrayList<String> getPrintInfo() {
        sortDeck();
        ArrayList<String> printInfo = new ArrayList<>();
        if (physicalDeck.size() == 0) {
            return printInfo;
        }
        Iterator<Card> deckIterator = physicalDeck.iterator();
        Card before = null;
        Card current = null;
        int cardinality = 1;
        while (deckIterator.hasNext()) {
            before = current;
            current = deckIterator.next();
            if (current.equals(before)) {
                cardinality++;
            } else {
                if (before != null) {
                    printInfo.add(String.format("%dx %s", cardinality, before.getDraftInfo()));
                }
                cardinality = 1;
            }
        }
        assert current != null;
        printInfo.add(String.format("%dx %s", cardinality, current.getDraftInfo()));
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
        sortDeck(Card.typeNameC);
    }

    private void sortDeck(Comparator<Card> comparator) {
        Collections.sort(physicalDeck, comparator);
    }
}
