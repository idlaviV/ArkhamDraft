package arkhamDraft;

import java.util.ArrayList;
import java.util.Random;

public class DraftingBox extends CardBox {

    private final ArrayList<Card> physicalDraftingBox = new ArrayList<>();
    private final ArrayList<ArrayList<CardFilter>> generatingFilters = new ArrayList<>();


    public DraftingBox() {
        super(new Card[0]);
    }

    public void addCards(CardBox filteredCardBox) {
        generatingFilters.add(filteredCardBox.getGeneratingFilters());
        getCards().addAll(filteredCardBox.getCards());
    }

    public void finalizeDraft(boolean secondCore) {
        for (Card card : this.getCards()) {
            if (secondCore || !card.getPack().equals("core") || card.getQuantity() > 1) {
                physicalDraftingBox.add(card.getPhysicalCard());
            }
            if (card.getQuantity() == 2) {
                physicalDraftingBox.add(card.getPhysicalCard());
            }
            physicalDraftingBox.add(card.getPhysicalCard());
        }
    }

    public int getPhysicalDraftingBoxSize() {
        return physicalDraftingBox.size();
    }

    public ArrayList<Card> draftCards(int number) {
        ArrayList<Card> draftedCards = new ArrayList<>();
        if (number <= physicalDraftingBox.size()) {
            Random rand = new Random();
            for (int i = 0; i < number; i++) {
                int position = rand.nextInt(physicalDraftingBox.size());
                draftedCards.add(physicalDraftingBox.get(position));
                physicalDraftingBox.remove(position);
            }
        }
        return draftedCards;
    }

    public ArrayList<ArrayList<CardFilter>> getAllGeneratingFilters() {
        return generatingFilters;
    }
}
