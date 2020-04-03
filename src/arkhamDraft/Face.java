package arkhamDraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Face {
    private Drafter drafter;
    private CardBox masterCardBox;
    private SettingsManager settingsManager;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[93m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_MULTICLASS = "\u001B[90m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public Face(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    public void watch() throws IOException {
        Scanner scanner = new Scanner(System.in);
        //scanner.useDelimiter("\n");
        boolean quit = false;
        while (!quit) {
            //String input = scanner.next();
            String input = scanner.nextLine();
            switch (input) {
                case "settings":
                    watchSettingsManager(scanner);
                    break;
                case "quit":
                    quit = true;
                    break;
                case "start draft":
                    drafter = new Drafter(settingsManager.getOwnedCards(masterCardBox), settingsManager.getSecondCore());
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
                    watchDraft(scanner);
                    break;
                case "increase draft deck":
                    if (drafter == null) {
                        System.out.println("Start your draft first with 'start draft'!");
                    } else {
                        drafter.initializeCardAddition();
                        System.out.println(String.format("Collected %d cards.", drafter.getFilteredBoxSize()));
                        watchFilter(scanner);
                    }
                    break;
                case "finalize draft deck":
                    if (drafter == null) {
                        System.out.println("Start your draft first with 'start draft'!");
                    } else {
                        drafter.finalizeDraft();
                        System.out.println("Draft deck finalized. You may now start to draft cards via 'draft:x',\n" +
                                "where 'x' is the number of cards you want to draft.");
                        System.out.println(String.format("The draft deck currently holds %d cards.", drafter.getPhysicalDraftingBoxSize()));
                        watchDraft(scanner);
                    }
                    break;
                case "help":
                    System.out.println("Usable commands are: 'start draft', 'increase draft deck', 'quit', 'settings'.");
                    break;//TODO: Update help
                default:
                    System.out.println("Need help? Type 'help'.");
                    System.out.println(input);
            }
        }
        scanner.close();
    }

    private void watchSettingsManager(Scanner scanner) throws IOException {
        boolean quit = false;
        while(!quit) {
            String input = scanner.next();
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
                        String input2 = scanner.next();
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

    private void watchDraft(Scanner scanner) {
        boolean quit = false;
        while(!quit) {
            String input = scanner.next();
            switch(input) {
                //TODO: Help is missing
                case "reset draft":
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
                case "show deck":
                    ArrayList<String> deckString = drafter.getDraftedDeck().getPrintInfo();
                    for (String cardString : deckString) {
                        System.out.println(cardString);
                    }
                    break;
                default:
                    ArrayList<String> arguments = watchDraftInputDecrypter(input);
                    switch (arguments.get(0)) {
                        case "draft":
                            int draftSize = Integer.parseInt(arguments.get(1));
                            ArrayList<Card> draftedCards = drafter.draftCards(draftSize);
                            if (draftedCards.isEmpty()) {
                                System.out.println("No cards drafted. Argument should be positive\n" +
                                        "and smaller than amount of cards in draft deck.");
                            } else {
                                printDraftedCards(draftedCards);
                            }
                            break;
                        case "add":
                            int addCardIndex = Integer.parseInt(arguments.get(1));
                            if (drafter.addCardToDeck(addCardIndex)) {
                                System.out.println(String.format("Card %d added to deck.", addCardIndex));
                                printDraftedCards(drafter.getDraftedCards());
                            } else {
                                System.out.println("There is no card at the specified position.");
                            }
                            break;
                        case "redraft":
                            int redraftIndex = Integer.parseInt(arguments.get(1));
                            drafter.redraftCard(redraftIndex);
                            printDraftedCards(drafter.getDraftedCards());
                            break;
                        default:
                            System.out.println("This is not a valid draft command. Try 'help' for help.");
                    }
            }
        }
    }

    private ArrayList<String> watchDraftInputDecrypter(String input) {
        ArrayList<String> arguments = new ArrayList<>();
        input = input.replaceAll(" +", " ").trim();
        if (input.matches("draft *: *\\d+")) {
            arguments.add("draft");
            arguments.add(input.replaceFirst("draft *: *",""));
        }
        if (input.matches("add *\\d+")) {
            arguments.add("add");
            arguments.add(input.replaceFirst("add *",""));
        }
        if (input.matches("redraft *\\d+")) {
            arguments.add("redraft");
            arguments.add(input.replaceFirst("redraft *",""));
        }
        if (arguments.size() == 0) arguments.add("Could not decrypt draft command.");
        return arguments;
    }

    private void printDraftedCards(ArrayList<Card> draftedCards) {
        for (int i = 0; i < draftedCards.size(); i++) {
            Card card = draftedCards.get(i);
            if (card != null) {
                System.out.println(String.format("%d) %s", i, card.getDraftInfo()));
            } else {
                System.out.println(String.format("%d)",i));
            }
        }
    }

    private void watchFilter(Scanner scanner) {
        boolean quit = false;
        while(!quit) {
            String input = scanner.next();
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
                    ArrayList<String> arguments = watchFilterInputDecrypter(input);
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

    private ArrayList<String> watchFilterInputDecrypter(String input) {
        ArrayList<String> arguments = new ArrayList<>();
        input = input.replaceAll(" +", " ").trim();
        if (input.matches(".*[^!]:.*") || input.matches(".*!:.*")) {
            arguments.add("containsFilter");
            if (input.matches(".*[^!]:.*")) {
                arguments.add(":");
            } else {
                arguments.add("!:");
            }
            arguments.add(input.replaceFirst(":.*","").trim());
            String[] parameters = input.replaceFirst(".*:","").split(",");
            for (String parameter : parameters) {
                arguments.add(parameter.trim());
            }
        } else if (input.matches(".*<.*|.*>.*|.*=.*")) {
            String relatorString = input.replaceAll("[^<>=!]","");
            if (relatorString.matches(Relator.relatorRegex)) {
                arguments.add("numericalFilter");
                arguments.add(relatorString);
                arguments.add(input.replaceFirst("<=.*|>=.*|=.*|<.*|>.*", "").trim());
                String[] parameters = input.replaceFirst(".*[<>=!]+", "").split(",");
                for (String parameter : parameters) {
                    arguments.add(parameter.trim());
                }
            } else {
                arguments.add("Could not decrypt filter.");
            }
        } else {
            arguments.add("Could not decrypt filter.");
        }
        return arguments;
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

}
