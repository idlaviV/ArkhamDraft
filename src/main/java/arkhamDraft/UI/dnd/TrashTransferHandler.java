package arkhamDraft.UI.dnd;

import arkhamDraft.Brain;
import arkhamDraft.CardPanel;

public class TrashTransferHandler extends CardTransferHandler {

    public TrashTransferHandler(Brain brain, Runnable updateAllPanels) {
        super(brain, updateAllPanels);
        setTo(CardPanel.TRASH);
    }

    boolean legalOrigin(CardPanel from) {
        switch (from) {
            case DRAFT:
                break;
            case DECK:
            case SIDEBOARD:
                return true;
            default:
        }
        return false;
    }

    @Override
    int fetchDropLocation(TransferSupport support) {
        return -1;
    }

    @Override
    void updateScroll(int row) {

    }
}
