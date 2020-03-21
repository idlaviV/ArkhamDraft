package arkhamDraft;

import java.util.function.Function;

public class Drafter {
    private DraftingBox draftingBox = new DraftingBox();
    private CardBox masterCardBox;
    private CardBox filteredCardBox;

    public Drafter(CardBox masterCardBox) {
        this.masterCardBox = masterCardBox;
    }

    public void initializeCardAddition(){
        filteredCardBox = new CardBox(masterCardBox);
    }

    public boolean filter(Function<Card, Boolean> filter){
        if (filteredCardBox == null) {
            return false;
        } else {
            filteredCardBox.filter(filter);
            return true;
        }
    }

    public void addCards(){
        draftingBox.addCards(filteredCardBox);
        filteredCardBox = null;
    }
}
