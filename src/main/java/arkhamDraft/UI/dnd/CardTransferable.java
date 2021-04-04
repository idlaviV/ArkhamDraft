package arkhamDraft.UI.dnd;

import arkhamDraft.Card;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class CardTransferable implements Transferable {

    public static DataFlavor cardFlavor = new DataFlavor(Card.class, "Card");
    private final Card card;

    public CardTransferable(Card card) {
        this.card = card;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] {cardFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return cardFlavor.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (isDataFlavorSupported(flavor)) {
            return card;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
