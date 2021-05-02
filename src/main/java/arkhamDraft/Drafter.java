package arkhamDraft;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Drafter {
    private final CardBox ownedCardBox; //all cards the drafter knows of
    private CardBox filteredCardBox; //can be filtered by the user, can be added to the draftingBox, only used by console
    private DraftingBox draftingBox = new DraftingBox(); //Box of cards which can be drafted from

    private Deck draftedCards = new Deck();
    private Deck deck = new Deck();
    private final Deck sideboard = new Deck();
    private boolean deckChangedFlag = false;

    private final boolean secondCore;
    private ArrayList<CardFilter> filterList = new ArrayList<>(); //only used by gui


    public Drafter(CardBox ownedCardBox, boolean secondCore) {
        this.ownedCardBox = ownedCardBox;
        this.secondCore = secondCore;
    }

    public Drafter(CardBox ownedCards, boolean secondCore, Deck deck, boolean changedDeckFlag) {
        this.ownedCardBox = ownedCards;
        this.secondCore = secondCore;
        this.deck = deck;
        this.deckChangedFlag = changedDeckFlag;
    }

    public void initializeCardAddition(){
        filteredCardBox = new CardBox(ownedCardBox);
        filterList = new ArrayList<>();
    }

    public void filter(CardFilter cardFilter){
        if (filteredCardBox == null) {
            System.err.println("FilteredCardBox is null");
        } else {
            filteredCardBox.filter(cardFilter);
        }
    }

    public void addCards() {
        draftingBox.addCards(filteredCardBox);
        filteredCardBox = null;
    }

    public void draftCards(int number) {
        ArrayList<Card> newCards = draftingBox.draftCards(number);
        if (!newCards.isEmpty()) {
            draftedCards = new Deck();
            draftedCards.addCards(newCards);
        }
    }

    public void redraftCard(int index) {
        if (index < draftedCards.getSize() && index >= 0) {
            List<Card> cards = draftingBox.draftCards(1);
            if (!cards.isEmpty()) {
                draftedCards.setCard(index, cards.get(0));
            } else {
                System.out.println("There are no cards in the draft deck left for redraft.");
            }
        }
    }

    public void redraftCard(Card card) {
        redraftCard(draftedCards.getIndex(card));
    }

    public void finalizeDraft() {
        draftingBox.finalizeDraft(secondCore, deck);
    }

    public void discardDraftingBox() {
        draftingBox = new DraftingBox();
    }

    public void addCardToDeckFromDraftedCards(Card card) {
        addCardFromDeckToDeck(draftedCards, deck, card);
        deckChangedFlag = true;
    }

    public void addCardToSideboard(Card card) {
        addCardFromDeckToDeck(draftedCards, sideboard, card);
    }

    public void addCardToDeckFromSideboard(Card card) {
        addCardFromDeckToDeck(sideboard, deck, card);
        deckChangedFlag = true;
    }

    private void addCardFromDeckToDeck(Deck fromDeck, Deck toDeck, int index) {
        if (index < fromDeck.getSize() && index >= 0 && fromDeck.getCard(index) != null && !fromDeck.getCard(index).equals(Card.nullCard)) {
            Card card = fromDeck.getCard(index);
            toDeck.addCard(card);
            fromDeck.removeCard(card);
        }
    }

    private void addCardFromDeckToDeck(Deck fromDeck, Deck toDeck, Card card, int index) {
        if (index <= toDeck.getSize() && index >= 0) {
            toDeck.addCard(card, index);
            fromDeck.removeCard(card);
        }
    }

    public void addCardFromDraftToDeck(Card card, int index) {
        addCardFromDeckToDeck(draftedCards, deck, card, index);
        deckChangedFlag = true;
    }

    public void addCardFromDraftToSide(Card card, int index) {
        addCardFromDeckToDeck(draftedCards, sideboard, card, index);
    }

    public void addCardFromSideToDeck(Card card, int index) {
        addCardFromDeckToDeck(sideboard, deck, card, index);
        deckChangedFlag = true;
    }

    private void addCardFromDeckToDeck(Deck fromDeck, Deck toDeck, Card card) {
        addCardFromDeckToDeck(fromDeck, toDeck, fromDeck.getIndex(card));
    }

    public void discardCardFromSideboard(Card card) {discardCardFromSideboard(sideboard.getIndex(card));}

    public void discardCardFromSideboard(int index) {
        discardCardFromAnyDeck(sideboard, index);
    }

    public void discardCardFromDeck(Card card) {
        discardCardFromDeck(deck.getIndex(card));
    }

    private void discardCardFromDeck(int index) {
        discardCardFromAnyDeck(deck, index);
        deckChangedFlag = true;
    }

    private void discardCardFromAnyDeck(Deck deck, int index) {
        if (index < deck.getSize() && index >= 0 && deck.getCard(index) != null) {
            deck.removeCard(deck.getCard(index));
        }
    }

    public void sortDeck(Comparator<Card> comparator) {
        deck.sortDeck(comparator);
    }

    public void sortDraftedCards(Comparator<Card> comparator) {
        draftedCards.sortDeck(comparator);
    }

    public void addToFilterList(CardFilter cardFilter) {
        filterList.add(cardFilter);
    }

    public void applyFilterList() {
        for (CardFilter filter : filterList) {
            filter(filter);
        }
    }

    public int getNumberOfCardsLeftAfterFiltering() {
        CardBox newCardBox = new CardBox(ownedCardBox);
        for (CardFilter filter : filterList) {
            newCardBox.filter(filter);
        }
        return newCardBox.getCards().size();
    }

    public void removeCardFilterFromList(CardFilter cardFilter) {
        filterList.remove(cardFilter);
    }


    public int getDraftingBoxSize() {
        return draftingBox.getCards().size();
    }

    public int getFilteredBoxSize(){
        if (filteredCardBox == null) {
            return -1;
        } else {
            return filteredCardBox.getCards().size();
        }
    }

    public Deck getDraftedCards() {
        return draftedCards;
    }

    public Deck getDeck() {
        return deck;
    }

    public Deck getSideboard() {
        return sideboard;
    }

    public ArrayList<ArrayList<CardFilter>> getCardFilterOfDraftingBox() {
        return draftingBox.getAllGeneratingFilters();
    }

    public ArrayList<CardFilter> getFilterList() {
        return filterList;
    }

    public void addCardsToDeck(List<Card> cards) {
        deck.addCards(cards);
        deckChangedFlag = true;
    }

    public void disposeDeck() {
        deck = new Deck();
        deckChangedFlag = false;
    }

    public void clear() {
        draftingBox = new DraftingBox();
        draftedCards.clear();
        sideboard.clear();
        filterList.clear();
    }

    public int getNumberOfCardsInPhysicalDraftingBox() {
        return draftingBox.getPhysicalDraftingBoxSize();
    }


    public int getNumberOfCardsInDeck() {
        deck.tidy();
        return deck.getSize();
    }

    public void updateFromNewDrafter(DraftingBox draftingBox, Deck deck, boolean newDeckChangedFlag) {
        clear();
        this.draftingBox = draftingBox;
        this.deck = deck;
        deckChangedFlag = newDeckChangedFlag;
    }

    public DraftingBox getDraftingBox() {
        return draftingBox;
    }

    @Override
    public String toString() {
        return "Drafter{"
                + draftingBox.getPhysicalDraftingBox().size()
                + "/"
                + draftedCards.getSize()
                + "/"
                + deck.getSize()
                + "/"
                + sideboard.getSize()
                + "}";
    }

    public boolean getChangedFlag() {
        return deckChangedFlag;
    }

    public void setChangeFlagToFalse() {
        deckChangedFlag = false;
    }
}
