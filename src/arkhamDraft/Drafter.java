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
}
