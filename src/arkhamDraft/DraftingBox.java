package arkhamDraft;

import java.util.ArrayList;
import java.util.Random;

public class DraftingBox extends CardBox {

    private ArrayList<Card> physicalDraftingBox = new ArrayList<>();

    public DraftingBox() {
        super(new Card[0]);
    }

    public void addCards(CardBox filteredCardBox) {
        getCards().addAll(filteredCardBox.getCards());
    }

    public void finalizeDraft() {
        for (Card card : this.getCards()) {
            physicalDraftingBox.add(card.getPhysicalCard());
            physicalDraftingBox.add(card.getPhysicalCard());
        }
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
}
