package arkhamDraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class Drafter {
    private DraftingBox draftingBox = new DraftingBox();
    private CardBox ownedCardBox;
    private CardBox filteredCardBox;
    private Deck draftedCards = new Deck();
    private Deck draftedDeck = new Deck();
    private Deck sideboard = new Deck();
    private boolean secondCore;

    public Drafter(CardBox ownedCardBox, boolean secondCore) {
        this.ownedCardBox = ownedCardBox;
        this.secondCore = secondCore;
    }

    public void initializeCardAddition(){
        filteredCardBox = new CardBox(ownedCardBox);
    }

    public boolean filter(CardFilter cardFilter){
        if (filteredCardBox == null) {
            return false;
        } else {
            filteredCardBox.filter(cardFilter);
            return true;
        }
    }

    public void addCards(){
        draftingBox.addCards(filteredCardBox);
        filteredCardBox = null;
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

    public void clear(){
        filteredCardBox = new DraftingBox();
    }

    public Deck draftCards(int number) {
        draftedCards.addCard(draftingBox.draftCards(number));
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

    public boolean addCardToDeck(int index) {
        return addCardFromDeckToDeck(draftedCards, draftedDeck, index);
    }

    public boolean addCardToSideboard(int index) {
        return addCardFromDeckToDeck(draftedCards, sideboard, index);
    }

    public boolean addCardToDeckFromSideboard(int index) {
        return addCardFromDeckToDeck(sideboard, draftedDeck, index);
    }

    public boolean addCardFromDeckToDeck(Deck fromDeck, Deck toDeck, int index) {
        if (index < fromDeck.getSize() && index >= 0 && fromDeck.getCard(index) != null) {
            toDeck.addCard(fromDeck.getCard(index));
            fromDeck.setCard(index, null);
            return true;
        }
        return false;
    }


    public void finalizeDraft() {
        draftingBox.finalizeDraft(secondCore);
    }

    public void discardDraftingBox() {
        draftingBox = new DraftingBox();
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

    public boolean discardCardFromSideboard(int index) {
        if (index < sideboard.getSize() && index >= 0 && sideboard.getCard(index) != null) {
            sideboard.setCard(index, null);
            return true;
        }
        return false;
    }

    public void tidySideboard() {
        sideboard.tidy();
    }
}
