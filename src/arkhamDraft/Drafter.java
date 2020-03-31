package arkhamDraft;

import java.util.ArrayList;
import java.util.List;


public class Drafter {
    private DraftingBox draftingBox = new DraftingBox();
    private CardBox ownedCardBox;
    private CardBox filteredCardBox;
    private ArrayList<Card> draftedCards = new ArrayList<>();
    private Deck draftedDeck = new Deck();
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

    public ArrayList<Card> draftCards(int number) {
        draftedCards = draftingBox.draftCards(number);
        return draftedCards;
    }

    public ArrayList<Card> redraftCard(int index) {
        if (index < draftedCards.size() && index >= 0) {
            List<Card> cards = draftingBox.draftCards(1);
            if (!cards.isEmpty()) {
                draftedCards.set(index, cards.get(0));
            } else {
                System.out.println("There are no cards in the draft deck left for redraft.");
            }
        }
        return draftedCards;
    }

    public boolean addCardToDeck(int index) {
        if (index < draftedCards.size() && index >= 0 && draftedCards.get(index) != null) {
            draftedDeck.addCard(draftedCards.get(index));
            draftedCards.set(index, null);
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

    public ArrayList<Card> getDraftedCards() {
        return draftedCards;
    }

    public Deck getDraftedDeck() {
        return draftedDeck;
    }
}
