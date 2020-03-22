package arkhamDraft;

import java.util.ArrayList;
import java.util.List;

public class DraftingBox extends CardBox {

    private ArrayList<Card> physicalDraftingBox;

    public DraftingBox() {
        super(new Card[0]);
    }

    public void addCards(CardBox filteredCardBox) {
        getCards().addAll(filteredCardBox.getCards());
    }

    public void finalizeDraft() {
        for (Card card : this.getCards()) {//add correct amout of cards
            physicalDraftingBox.add(card.getPhysicalCard());
        }
    }
}
