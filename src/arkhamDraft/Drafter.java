package arkhamDraft;

import java.util.ArrayList;


public class Drafter {
    private DraftingBox draftingBox = new DraftingBox();
    private CardBox ownedCardBox;
    private CardBox filteredCardBox;
    private ArrayList<Card> draftedCards = new ArrayList<>();
    private Deck draftedDeck = new Deck();

    public Drafter(CardBox ownedCardBox) {
        this.ownedCardBox = ownedCardBox;
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
        if (index <= draftedCards.size()) {
            draftedCards.set(index, draftingBox.draftCards(1).get(0));
        }
        return draftedCards;
    }

    public boolean addCardToDeck(int index) {
        draftedDeck.addCard(draftedCards.get(index));
        draftedCards.set(index, null);
        return true;
    }

    public void finalizeDraft() {
        draftingBox.finalizeDraft();
    }

    public void discardDraftingBox() {
        draftingBox = new DraftingBox();
    }
}