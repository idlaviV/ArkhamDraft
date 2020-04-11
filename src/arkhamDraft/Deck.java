package arkhamDraft;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
        Card before;
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

    public void tidy() {
        physicalDeck = physicalDeck.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
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

    public Card getCard(int index) {return physicalDeck.get(index);}

    public int getSize() {
        return physicalDeck.size();
    }

    public void setCard(int index, Card card) {
        physicalDeck.set(index, card);
    }

    private void sortDeck() {
        sortDeck(Card.typeNameC);
    }

    private void sortDeck(Comparator<Card> comparator) {
        physicalDeck.sort(comparator);
    }

    public ArrayList<String> getPrintInfoEnumerated() {
        ArrayList<String> printInfo = new ArrayList<>();
        int length = physicalDeck.size();
        int numberLength;
        if (length == 1) {
            numberLength = 1;
        } else {
            numberLength = (int) (Math.log10(length - 1) + 1);
        }
        for (int i = 0; i < length; i++) {
            Card card = physicalDeck.get(i);
            String output = String.format("%s) ", String.format("%1$" + numberLength + "d", i));
            if (card != null) {
                output = String.format("%s%s", output, card.getDraftInfo());
            }
            printInfo.add(output);
        }
        return printInfo;
    }
}
