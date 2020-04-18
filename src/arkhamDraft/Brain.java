package arkhamDraft;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Brain {
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
    private Scanner scanner;
    private Face face;


    public Brain(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
        face = new Face(this);
        face.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        face.setTitle("ArkhamDraft");
        face.setSize(1000,620);
        face.setResizable(false);
        face.setLocation(50,50);
        face.setVisible(true);
        face.initComponents();
    }

    public void watch() throws IOException {
        scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            String input = scanner.nextLine();
            switch (input) {
                case "settings":
                    watchSettingsManager(scanner);
                    break;
                case "quit":
                    quit = true;
                    break;
                case "start draft":
                    startDraft();
                    System.out.println("Empty draft deck created. Type 'increase draft deck' to start adding cards.");
                    break;
                case "test draft":
                    drafter = new Drafter(settingsManager.getOwnedCards(masterCardBox), settingsManager.getSecondCore());
                    drafter.initializeCardAddition();
                    drafter.filter(Card.generateCardFilter("faction", Relator.getContainRelator(":"), Collections.singletonList("guardian")));
                    drafter.filter(Card.generateCardFilter("xp", Relator.getNumericalRelator("="), 0));
                    drafter.addCards();
                    drafter.finalizeDraft();
                    System.out.println(String.format("Added all faction:guardian, xp=0 cards. There are %d of them.", drafter.getPhysicalDraftingBoxSize()));
                    watchDraft();
                    break;
                case "increase draft deck":
                    increaseDraftDeck();
                    break;
                case "finalize draft deck":
                    finalizeDraftDeck();
                    break;
                case "help":
                    System.out.println("Usable commands are: 'start draft', 'increase draft deck', 'quit', 'settings'.");
                    break;//TODO: Update help
                default:
                    System.out.println("Need help? Type 'help'.");
            }
        }
        scanner.close();
    }

    private void watchSettingsManager(Scanner scanner) throws IOException {
        boolean quit = false;
        while(!quit) {
            String input = scanner.nextLine();
            switch (input) {
                case "owned cards":
                    if (settingsManager.setOwnedPacks(scanner, new File("data/packs.txt"))) {
                        System.out.println("Owned packs updated.");
                    }
                    break;
                case "regular cards":
                    System.out.println("Do you want to use only regular cards? (y/n)");
                    boolean answered = false;
                    while(!answered) {
                        String input2 = scanner.nextLine();
                        switch (input2) {
                            case "y":
                                if (settingsManager.toggleRegular(new File("data/packs.txt"),true)) {
                                    answered = true;
                                    System.out.println("Using only regular cards now.");
                                }
                                break;
                            case "n":
                                if (settingsManager.toggleRegular(new File("data/packs.txt"),false)) {
                                    answered = true;
                                    System.out.println("Using all cards now.");
                                }
                            default:
                        }
                    }

                    break;
                case "help":
                    System.out.println("Usable commands are: 'owned cards', 'regular cards' and 'quit'.");
                    break;
                case "update":
                    try {settingsManager.updateDatabaseFromAPI();} catch (java.net.MalformedURLException e){System.out.println(e);}
                    updateFromJson();
                    break;
                case "quit":
                    System.out.println("Back to the main menu.");
                    quit = true;
                    break;
                default:
                    System.out.println("Need help? Type 'help'.");
            }
        }
    }

    private void watchDraft() {
        boolean quit = false;
        while(!quit) {
            String input = scanner.nextLine();
            switch(input) {
                //TODO: Help is missing
                case "reset draft"://TODO: This command does nothing helpful
                    drafter.discardDraftingBox();
                    drafter.finalizeDraft();
                    System.out.println(String.format("Draft deck now has %d cards again.", drafter.getPhysicalDraftingBoxSize()));
                    break;
                case "discard":
                    drafter.discardDraftingBox();
                    System.out.println("Draft deck discarded.");
                case "quit":
                    quit = true;
                    break;
                case "debug":
                    break;
                case "show deck":
                    ArrayList<String> deckString = drafter.getDraftedDeck().getPrintInfo(true);
                    for (String cardString : deckString) {
                        System.out.println(cardString);
                    }
                    break;
                case "save deck":
                    try {
                        if (saveDeck(scanner)) {
                            System.out.println("Deck saved.");
                        }
                    } catch (IOException e) {
                        System.out.println("Problem with file");
                        System.out.println(e);
                    }
                    break;
                case "show sideboard":
                    if (printSideboard()) {
                        watchSideboard(scanner);
                    }
                    break;
                default:
                    ArrayList<String> arguments = Decoder.watchDraftInputDecrypter(input);
                    switch (arguments.get(0)) {
                        case "draft":
                            int draftSize = Integer.parseInt(arguments.get(1));
                            Deck draftedCards = drafter.draftCards(draftSize);
                            if (draftedCards.getCards().isEmpty()) {
                                System.out.println("No cards drafted. Argument should be positive\n" +
                                        "and smaller than amount of cards in draft deck.");
                            } else {
                                printCardsEnumerated(draftedCards);
                                face.printCardsToDraftPanel(draftedCards);
                            }
                            break;
                        case "add":
                            int addCardIndex = Integer.parseInt(arguments.get(1));
                            if (drafter.addCardToDeck(addCardIndex)) {
                                System.out.println(String.format("Card %d added to deck.", addCardIndex));
                                face.printCardsToDeckPanel(drafter.getDraftedDeck());
                                printCardsEnumerated(drafter.getDraftedCards());
                                face.printCardsToDraftPanel(drafter.getDraftedCards());
                            } else {
                                System.out.println("There is no card at the specified position.");
                            }
                            break;
                        case "redraft":
                            int redraftIndex = Integer.parseInt(arguments.get(1));
                            drafter.redraftCard(redraftIndex);
                            printCardsEnumerated(drafter.getDraftedCards());
                            face.printCardsToDraftPanel(drafter.getDraftedCards());
                            break;
                        case "addSideboard":
                            int addSideboardIndex = Integer.parseInt(arguments.get(1));
                            if(drafter.addCardToSideboard(addSideboardIndex)) {
                                System.out.println(String.format("Card %d added to sideboard.",addSideboardIndex));
                                face.printCardsToSideboardPanel(drafter.getSideboard());
                                printCardsEnumerated(drafter.getDraftedCards());
                                face.printCardsToDraftPanel(drafter.getDraftedCards());
                            } else {
                                System.out.println("There is no card at the specified position.");
                            }
                            break;
                        default:
                            System.out.println("This is not a valid draft command. Try 'help' for help.");
                    }
            }
        }
    }

    private void watchSideboard(Scanner scanner) {
        boolean quit = false;
        while (!quit) {
            String input = scanner.nextLine();
            switch (input) {
                case "back":
                    quit = true;
                    drafter.tidySideboard();
                    printCardsEnumerated(drafter.getDraftedCards());
                    break;
                default:
                    ArrayList<String> arguments = Decoder.watchDraftInputDecrypter(input);
                    switch (arguments.get(0)) {
                        case "add":
                            int addCardIndex = Integer.parseInt(arguments.get(1));
                            if (drafter.addCardToDeckFromSideboard(addCardIndex)) {
                                System.out.println(String.format("Card %d added to deck from sideboard.", addCardIndex));
                                face.printCardsToDeckPanel(drafter.getDraftedDeck());
                                printSideboard();
                                face.printCardsToSideboardPanel(drafter.getSideboard());
                            } else {
                                System.out.println("There is no card in the sideboard on the specified position.");
                            }
                            break;
                        case "discard":
                            int discardCardIndex = Integer.parseInt(arguments.get(1));
                            if (drafter.discardCardFromSideboard(discardCardIndex)) {
                                System.out.println(String.format("Card %d discarded from sideboard.", discardCardIndex));
                                printSideboard();
                                face.printCardsToSideboardPanel(drafter.getSideboard());
                            } else {
                                System.out.println("There is no card in the sideboard on the specified position.");
                            }
                            break;
                        default:
                            System.out.println("This is not a valid sideboard command.");
                    }
            }
        }
    }

    private void watchFilter() {
        boolean quit = false;
        while(!quit) {
            String input = scanner.nextLine();
            switch(input){
                case "quit":
                    quit = true;
                    drafter.clear();
                    break;
                case "add cards":
                    quit = true;
                    break;
                case "help":
                    System.out.println("Add a filter like 'faction:guardian' or 'xp=3'. Or add all those filtered cards" +
                            "to the drafting deck via 'add cards'.");
                    //TODO: Help should be improved.
                    break;
                default :
                    ArrayList<String> arguments = Decoder.watchFilterInputDecrypter(input);
                    switch (arguments.get(0)) {
                        case "containsFilter":
                            drafter.filter(Card.generateCardFilter(arguments.get(2), Relator.getContainRelator(arguments.get(1)), arguments.subList(3, arguments.size())));
                            System.out.println(String.format("%d cards left.",drafter.getFilteredBoxSize()));
                            break;
                        case "numericalFilter":
                            try {
                                int value = Integer.parseInt(arguments.get(3));
                                drafter.filter(Card.generateCardFilter(arguments.get(2), Relator.getNumericalRelator(arguments.get(1)), value));
                                System.out.println(String.format("%d cards left.", drafter.getFilteredBoxSize()));
                            } catch (NumberFormatException e) {
                                System.out.println("Error: value of filter is not an integer.");
                            }
                            break;
                        default:
                            notWellFormatted();
                    }
            }
        }
        int preAdd = drafter.getDraftingBoxSize();
        drafter.addCards();
        System.out.println(String.format("%d card(s) added to draft deck. Finalize your deck via 'finalize draft deck' or add more cards.", drafter.getDraftingBoxSize() - preAdd));
    }

    private void startDraft() {
        drafter = new Drafter(settingsManager.getOwnedCards(masterCardBox), settingsManager.getSecondCore());
    }

    private void increaseDraftDeck() {
        if (drafter == null) {
            System.out.println("Start your draft first with 'start draft'!");
        } else {
            drafter.initializeCardAddition();
            System.out.println(String.format("Collected %d cards.", drafter.getFilteredBoxSize()));
            watchFilter();
        }
    }

    private void finalizeDraftDeck(){
        if (drafter == null) {
            System.out.println("Start your draft first with 'start draft'!");
        } else {
            drafter.finalizeDraft();
            System.out.println("Draft deck finalized. You may now start to draft cards via 'draft:x',\n" +
                    "where 'x' is the number of cards you want to draft.");
            System.out.println(String.format("The draft deck currently holds %d cards.", drafter.getPhysicalDraftingBoxSize()));
            watchDraft();
        }
    }

    private boolean saveDeck(Scanner scanner) throws IOException {
        System.out.println("Please type the name of the deck:");
        String fileName = scanner.nextLine();
        File deckFile = new File(String.format("data/decks/%s.txt", fileName));
        if (deckFile.exists()) {
            boolean overwrite = false;
            while (!overwrite) {
                System.out.println("Deck already exists. Overwrite? (y/n)");
                String overwriteString = scanner.nextLine();
                switch (overwriteString) {
                    case "y":
                        overwrite = true;
                        deckFile.delete();
                        deckFile.createNewFile();
                        break;
                    case "n":
                        return false;
                }
            }
        }
        FileWriter fileWriter = new FileWriter(deckFile);
        fileWriter.write(fileName + "\n" + "\n");
        ArrayList<String> printInfo = drafter.getDraftedDeck().getPrintInfo(false);
        for (String println: printInfo) {
            fileWriter.write(println+"\n");
        }
        fileWriter.close();
        return true;
    }

    private boolean printSideboard() {
        if (drafter.getSideboard().getSize() == 0) {
            System.out.println("Sideboard is empty");
            return false;
        } else {
            printCardsEnumerated(drafter.getSideboard());
            return true;
        }
    }

    private void printCardsEnumerated(Deck draftedCardsDeck) {
        ArrayList<String> deckString = draftedCardsDeck.getPrintInfoEnumerated(true);
        for (String cardString : deckString) {
            System.out.println(cardString);
        }
    }

    private boolean updateSettingsFromFile() {
        return settingsManager.updateSettings(new File("data/packs.txt"));
    }

    private void notWellFormatted() {
        System.out.println("Filter not well formatted! Type 'help' to get help on filters \n" +
                "or 'quit' to discard the selected cards \n" +
                "or 'add cards' to add the selected cards to the draft deck.");
    }

    public void updateFromJson() throws IOException {
        masterCardBox = settingsManager.updateDatabaseFromJSON();
    }

    public void sortDeck(String sortBy) {
        drafter.sortDeck(Decoder.decryptComparator(sortBy));
        face.printCardsToDeckPanel(drafter.getDraftedDeck());
    }

}
