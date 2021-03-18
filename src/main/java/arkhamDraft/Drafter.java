package arkhamDraft;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Drafter {
    private final CardBox ownedCardBox; //all cards the drafter knows of
    private CardBox filteredCardBox; //can be filtered by the user, can be added to the draftingBox, only used by console
    private DraftingBox draftingBox = new DraftingBox(); //Box of cards which can be drafted from

    private Deck draftedCards = new Deck();
    private Deck draftedDeck = new Deck();
    private final Deck sideboard = new Deck();

    private final boolean secondCore;
    private ArrayList<CardFilter> filterList = new ArrayList<>(); //only used by gui


    public Drafter(CardBox ownedCardBox, boolean secondCore) {
        this.ownedCardBox = ownedCardBox;
        this.secondCore = secondCore;
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

    public void addCards(){
        draftingBox.addCards(filteredCardBox);
        filteredCardBox = null;
    }

    public void clearFilter(){
        filteredCardBox = new DraftingBox();
    }

    public Deck draftCards(int number) {
        ArrayList<Card> newCards = draftingBox.draftCards(number);
        if (!newCards.isEmpty()) {
            draftedCards = new Deck();
            draftedCards.addCards(newCards);
        }
        return draftedCards;
    }

    public Deck redraftCard(int index) {
        if (index < draftedCards.getSize() && index >= 0) {
            List<Card> cards = draftingBox.draftCards(1);
            if (!cards.isEmpty()) {
                draftedCards.setCard(index, cards.get(0));
            } else {
                System.out.println("There are no cards in the draft deck left for redraft.");
            }
        }
        return draftedCards;
    }

    public void redraftCard(Card card) {
        redraftCard(draftedCards.getIndex(card));
    }

    public void finalizeDraft() {
        draftingBox.finalizeDraft(secondCore, draftedDeck);
    }

    public void discardDraftingBox() {
        draftingBox = new DraftingBox();
    }

    public boolean addCardToDeckFromDraftedCards(int index) {
        return addCardFromDeckToDeck(draftedCards, draftedDeck, index);
    }

    public void addCardToDeckFromDraftedCards(Card card) {
        addCardFromDeckToDeck(draftedCards, draftedDeck, card);
    }

    public boolean addCardToSideboard(int index) {
        return addCardFromDeckToDeck(draftedCards, sideboard, index);
    }

    public void addCardToSideboard(Card card) {
        addCardFromDeckToDeck(draftedCards, sideboard, card);
    }

    public boolean addCardToDeckFromSideboard(int index) {
        return addCardFromDeckToDeck(sideboard, draftedDeck, index);
    }

    public void addCardToDeckFromSideboard(Card card) {
        addCardFromDeckToDeck(sideboard, draftedDeck, card);
    }

    private boolean addCardFromDeckToDeck(Deck fromDeck, Deck toDeck, int index) {
        if (index < fromDeck.getSize() && index >= 0 && fromDeck.getCard(index) != null && !fromDeck.getCard(index).equals(Card.nullCard)) {
            toDeck.addCard(fromDeck.getCard(index));
            fromDeck.setCard(index, Card.nullCard);
            return true;
        }
        return false;
    }

    private void addCardFromDeckToDeck(Deck fromDeck, Deck toDeck, Card card) {
        addCardFromDeckToDeck(fromDeck, toDeck, fromDeck.getIndex(card));
    }

    public void discardCardFromSideboard(Card card) {discardCardFromSideboard(sideboard.getIndex(card));}

    public boolean discardCardFromSideboard(int index) {
        return discardCardFromAnyDeck(sideboard, index);
    }

    public void discardCardFromDraftedDeck(Card card) {
        discardCardFromDraftedDeck(draftedDeck.getIndex(card));
    }

    private void discardCardFromDraftedDeck(int index) {
        discardCardFromAnyDeck(draftedDeck, index);
    }

    private boolean discardCardFromAnyDeck(Deck deck, int index) {
        if (index < deck.getSize() && index >= 0 && deck.getCard(index) != null) {
            deck.setCard(index, Card.nullCard);
            return true;
        }
        return false;
    }

    public void tidySideboard() {
        sideboard.tidy();
    }

    public void sortDeck(Comparator<Card> comparator) {
        draftedDeck.sortDeck(comparator);
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

    public int getPhysicalDraftingBoxSize() {
        return draftingBox.getPhysicalDraftingBoxSize();
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

    public Deck getDraftedDeck() {
        return draftedDeck;
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
        draftedDeck.addCards(cards);
    }

    public void disposeDeck() {
        draftedDeck = new Deck();
    }

    public void clear() {
        draftingBox = new DraftingBox();
        draftedCards.clear();
        sideboard.clear();
        filterList.clear();
    }

    public int getNumberOfCardsInDraftingDeck() {
        return draftingBox.getPhysicalDraftingBoxSize();
    }


    public int getNumberOfCardsInDeck() {
        draftedDeck.tidy();
        return draftedDeck.getSize();
    }
}
