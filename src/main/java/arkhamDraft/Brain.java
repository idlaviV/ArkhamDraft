package arkhamDraft;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

public interface Brain {

    void guiOpensNewDraftDeckDialog();

    void updateFromJson () throws IOException;

    void watch() throws IOException;

    int getDraftingBoxSize();

    ArrayList<CardFilter> getFilterList();

    void guiEntersFilterCardsDialog();

    void addCards();

    void guiAddToDeck(ArrayList<Card> checkedCards);

    Deck getDraftedCards();

    void guiLeavesFilterCardsDialog();

    int getNumberOfCardsLeftAfterFiltering();

    void addFilterFromGUI(ArrayList<String> arguments);

    Deck getDraftedDeck();

    Deck getSideboard();

    void guiAddFromSideboard(ArrayList<Card> checkedCards);

    void guiDiscardFromSideboard(ArrayList<Card> checkedCards);

    void guiAddToSideboard(ArrayList<Card> checkedCards);

    void guiDeleteDeck();

    void guiDraftCardsNew(int numberOfDraftedCards);

    void guiFinalizeDraftDeck();

    void guiRedraft(ArrayList<Card> checkedCards);

    void removeCardFilterFromList(CardFilter cardFilter);

    void sortDeck(String requireNonNull);

    void guiSaveFilterList(File file);

    void loadFilterList(File file, Function<Boolean, SwingWorker<Integer, Void>> addCards);

    void guiSaveDeck(File file) throws IOException;
}
