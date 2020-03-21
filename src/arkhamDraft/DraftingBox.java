package arkhamDraft;

import java.util.ArrayList;
import java.util.List;

public class DraftingBox extends CardBox {

    public DraftingBox() {
        super(new Card[0]);
    }

    public void addCards(CardBox filteredCardBox) {
        getCards().addAll(filteredCardBox.getCards());
    }
}
