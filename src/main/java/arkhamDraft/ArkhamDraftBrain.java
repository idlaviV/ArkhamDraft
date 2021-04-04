package arkhamDraft;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.function.Supplier;

import static java.lang.Thread.sleep;

public class ArkhamDraftBrain implements Brain{
    private Drafter drafter;
    private CardBox masterCardBox;
    private final SettingsManager settingsManager;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[93m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_MULTICLASS = "\u001B[90m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public ArkhamDraftBrain(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    public void generateFilter(String input) {
        ArrayList<String> arguments = Decoder.watchFilterInputDecrypter(input);
        switch (arguments.get(0)) {
            case "containsFilter":
                drafter.filter(Card.generateCardFilter(arguments.get(2), Relator.getContainRelator(arguments.get(1)), arguments.subList(3, arguments.size()), arguments.get(1)));
                System.out.printf("%d cards left.%n",drafter.getFilteredBoxSize());
                break;
            case "numericalFilter":
                try {
                    int value = Integer.parseInt(arguments.get(3));
                    drafter.filter(Card.generateCardFilter(arguments.get(2), Relator.getNumericalRelator(arguments.get(1)), value, arguments.get(1)));
                    System.out.printf("%d cards left.%n", drafter.getFilteredBoxSize());
                } catch (NumberFormatException e) {
                    System.out.println("Error: value of filter is not an integer.");
                }
                break;
            default:
                notWellFormatted();
        }
    }

    private void startDraft() {
        drafter = new Drafter(settingsManager.getOwnedCards(masterCardBox), settingsManager.getSecondCore());
    }

    private void notWellFormatted() {
        System.out.println("Filter not well formatted!");
    }

    public void updateFromJson() throws IOException {
        masterCardBox = settingsManager.updateDatabaseFromJSON();
    }

    public void sortDeck(String sortBy) {
        drafter.sortDeck(Decoder.decryptComparator(sortBy));
    }
    public void sortDraftedCards(String sortBy) {
        drafter.sortDraftedCards(Decoder.decryptComparator(sortBy));
    }



    public void addFilterFromGUI(ArrayList<String> arguments) {
        CardFilter newCardFilter = null;
        switch (arguments.get(0)) {
            case "containsFilter":
                newCardFilter = Card.generateCardFilter(arguments.get(2), Relator.getContainRelator(arguments.get(1)), arguments.subList(3, arguments.size()), arguments.get(1));
                break;
            case "numericalFilter":
                try {
                    int value = Integer.parseInt(arguments.get(3));
                    newCardFilter = Card.generateCardFilter(arguments.get(2), Relator.getNumericalRelator(arguments.get(1)), value, arguments.get(1));
                } catch (NumberFormatException e) {
                    System.out.println("Error: value of filter is not an integer.");
                }
                break;
        }
        drafter.addToFilterList(newCardFilter);
    }

    public void guiEntersFilterCardsDialog() {
        drafter.initializeCardAddition();
    }

    public void guiLeavesFilterCardsDialog() {
        drafter.applyFilterList();
    }

    @Override
    public void guiCreateNewDeck() {
        guiOpensNewDraftDeckDialog();
        clearDrafter();
        drafter.disposeDeck();
    }

    public void loadFilterList(File file, Supplier<SwingWorker<Integer, Void>> addCards) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
                    initializeCardAddition();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.matches("=*")) {
                            SwingWorker<Integer, Void> sw = addCards.get();
                            while(sw.getProgress()!=100) {
                                sleep(100);//Todo
                            }
                            initializeCardAddition();
                        } else {
                            generateFilter(line);
                        }
                    }
                    addCards.get();
                    reader.close();
                    fr.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guiSaveDeck(File file) throws IOException {
        String fileName = file.getName().split("\\.")[0];
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(fileName + "\n" + "\n");
        fileWriter.write("This line must contain investigator name" + "\n" + "\n");
        ArrayList<String> printInfo = drafter.getDraftedDeck().getPrintInfo(false);
        for (String println: printInfo) {
            fileWriter.write(println+"\n");
        }
        fileWriter.close();
    }

    @Override
    public void buildDeckFromFile(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            while ((line = reader.readLine()) != null) {
                if (Decoder.isThisACardEntry(line)) {
                    List<Card> foundCards = Decoder.findCardInCardBoxFromString(masterCardBox, line);
                    if (!foundCards.isEmpty()) {
                        drafter.addCardsToDeck(foundCards);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disposeDeck() {
        drafter.disposeDeck();
    }

    public void guiOpensNewDraftDeckDialog() {
        if (drafter == null) {
            startDraft();
        }
        drafter.discardDraftingBox();
    }

    public void guiDraftCardsNew(int amount) {
        drafter.draftCards(amount);
    }

    public void guiFinalizeDraftDeck() {
        drafter.finalizeDraft();
    }

    public void removeCardFilterFromList(CardFilter cardFilter) {
        drafter.removeCardFilterFromList(cardFilter);
    }

    public boolean guiInsertCardFromPanelToPanel(CardPanel from, CardPanel to, Card card, int position) {
        switch (from) {
            case DRAFT:
                if (CardPanel.DECK.equals(to)) {
                    drafter.addCardFromDraftToDeck(card, position);
                    return true;
                }
                if (CardPanel.SIDEBOARD.equals(to)) {
                    drafter.addCardFromDraftToSide(card, position);
                    return true;
                }
                break;
            case DECK:
                break;
            case SIDEBOARD:
                if (CardPanel.DECK.equals(to)) {
                    drafter.addCardFromSideToDeck(card, position);
                    return true;
                }
                break;
            default:
        }
        return false;
    }

    public void guiRedraft(ArrayList<Card> checkedCards) {
        for (Card currentCard : checkedCards) {
            drafter.redraftCard(currentCard);
        }
    }

    public void guiAddToDeck(ArrayList<Card> checkedCards) {
        for (Card currentCard : checkedCards) {
            drafter.addCardToDeckFromDraftedCards(currentCard);
        }
    }

    public void guiAddToSideboard(ArrayList<Card> checkedCards) {
        for (Card currentCard : checkedCards) {
            drafter.addCardToSideboard(currentCard);
        }
    }

    @Override
    public void guiDiscardFromDraftedDeck(ArrayList<Card> checkedCards) {
        for (Card currentCard : checkedCards) {
            drafter.discardCardFromDraftedDeck(currentCard);
        }
    }

    public void guiAddFromSideboard(ArrayList<Card> checkedCards) {
        for (Card currentCard : checkedCards) {
            drafter.addCardToDeckFromSideboard(currentCard);
        }
    }

    public void guiDiscardFromSideboard(ArrayList<Card> checkedCards) {
        for (Card currentCard : checkedCards) {
            drafter.discardCardFromSideboard(currentCard);
        }
    }

    public void guiSaveFilterList(File saveFile) {
        try {
            if(!saveFile.createNewFile()){
                saveFile.delete();
                saveFile.createNewFile();
            }
            FileWriter fw = new FileWriter(saveFile);
            ArrayList<ArrayList<CardFilter>> filterListAll = drafter.getCardFilterOfDraftingBox();
            Iterator<ArrayList<CardFilter>> iterator = filterListAll.iterator();
            while(iterator.hasNext()) {
                ArrayList<CardFilter> filterList = iterator.next();
                for (CardFilter filter : filterList) {
                    fw.write(filter.toString() + "\n");
                }
                if (iterator.hasNext()) {
                    fw.write("=====\n");
                }
            }
            fw.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void guiDeleteDeck() {
        drafter.discardDraftingBox();
    }

    public Deck getDraftedCards() {
        return drafter.getDraftedCards();
    }

    public Deck getDraftedDeck() {
        return drafter.getDraftedDeck();
    }

    public Deck getSideboard() {
        return drafter.getSideboard();
    }

    public int getNumberOfCardsLeftAfterFiltering() {
        return drafter.getNumberOfCardsLeftAfterFiltering();
    }

    public ArrayList<CardFilter> getFilterList() {
        return drafter.getFilterList();
    }

    public int getDraftingBoxSize() {
        return drafter.getDraftingBoxSize();
    }

    public void addCards() {
        drafter.addCards();
    }

    public void initializeCardAddition() {
        drafter.initializeCardAddition();
    }

    public void clearDrafter() {
        drafter.clear();
    }

    @Override
    public int getNumberOfCardsInDraftingDeck() {
        return drafter.getNumberOfCardsInDraftingDeck();
    }

    @Override
    public int getNumberOfCardsInDeck() {
        return drafter.getNumberOfCardsInDeck();
    }

    @Override
    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
}
