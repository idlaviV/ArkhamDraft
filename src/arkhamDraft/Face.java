package arkhamDraft;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Face {
    private Drafter drafter;
    private CardBox masterCardBox;
    private PackManager packManager;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[93m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_MULTICLASS = "\u001B[90m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public Face(CardBox masterCardBox, PackManager packManager) {
        this.masterCardBox = masterCardBox;
        this.packManager = packManager;
        updatePackManagerFromFile();
    }

    public void watch(){
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        boolean quit = false;
        while (!quit) {
            String input = scanner.next();
            switch (input) {
                case "pack manager":
                    watchPackManager(scanner);
                    break;
                case "quit":
                    quit = true;
                    break;
                case "start draft":
                    drafter = new Drafter(packManager.getOwnedCards(masterCardBox));
                    System.out.println("Empty draft deck created. Type 'increase draft deck' to start adding cards.");
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
                        watchDraft(scanner);
                    }
                    break;
                case "help":
                    System.out.println("Usable commands are: 'start draft', 'increase draft deck','quit'.");
                    break;//TODO: Update help
                default:
                    System.out.println("Need help? Type 'help'.");
            }
        }
        scanner.close();
    }

    private void watchPackManager(Scanner scanner) {
        boolean quit = false;
        while(!quit) {
            String input = scanner.next();
            switch (input) {
                case "set owned cards":
                    if (packManager.setOwnedPacks(scanner, new File("data/packs.txt"))) {
                        System.out.println("Owned packs updated.");
                    }
                    break;
                case "regular cards":
                    //TODO: toggle regular cards on/off
                    break;
                    //TODO: Help is missing
                case "quit":
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
                case "discard":
                    drafter.discardDraftingBox();
                    System.out.println("Draft deck discarded.");
                case "quit":
                    quit = true;
                    break;
                default:
                    Integer number = decryptDraft(input);
                    if (number == null) {
                        System.out.println("This is not a valid draft command. Try 'help' for help.");
                    } else {
                        ArrayList<Card> draftedCards = drafter.draftCards(number);
                        if (draftedCards.isEmpty()) {
                            System.out.println("No cards drafted. Argument should be positive\n" +
                                    "and smaller then amount of cards in draft deck.");
                        } else {
                            for (Card card: draftedCards) {
                                System.out.println(card.getFactionColor() + card.getDraftInfo() + ANSI_RESET);
                            }
                        }
                    }
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
                    //TODO: Help is missing
                    break;
                default :
                    String[] inputParts = input.split(Relator.relatorRegex);
                    if (inputParts.length != 2) {
                        notWellFormatted();
                    } else {
                        decryptFilter(input);
                    }
            }
        }
        int preAdd = drafter.getDraftingBoxSize();
        drafter.addCards();
        System.out.println(String.format("%d card(s) added to draft deck.",drafter.getDraftingBoxSize() - preAdd));
    }

    private Integer decryptDraft(String input) {
        String[] inputParts = input.split(":");
        if (inputParts.length != 2) {
            return null;
        }
        inputParts[0] = inputParts[0].trim();
        if (!inputParts[0].equals("draft")) {
            return null;
        }
        inputParts[1] = inputParts[1].trim();
        int number;
        try {
            number = Integer.parseInt(inputParts[1]);
        } catch (NumberFormatException e) {
            return null;
        }
        return number;
    }

    private boolean decryptFilter(String input) {
        String[] inputParts = input.split(Relator.relatorRegex);
        inputParts[0] = inputParts[0].trim();
        inputParts[1] = inputParts[1].trim();
        Matcher matcher = Pattern.compile(Relator.relatorRegex).matcher(input);
        matcher.find();
        String relatorString = matcher.group();
        boolean relatorIsNumerical = !Relator.isContainRelator(relatorString);
        if (relatorIsNumerical) {
            int value;
            try {
                value = Integer.parseInt(inputParts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Error: value of filter is not an integer.");
                return false;
            }
            drafter.filter(Card.generateCardFilter(inputParts[0],
                    Relator.getNumericalRelator(relatorString),
                    value));
        } else{
            /*if (inputParts[0].equals("text"))
            {
                drafter.filter(Card.generateCardFilter(inputParts[0],
                        inputParts[1]));
            } else {*/
                drafter.filter(Card.generateCardFilter(inputParts[0],
                        Relator.getContainRelator(relatorString),
                        inputParts[1]));
            //}
        }
        System.out.println(String.format("%d cards left.",drafter.getFilteredBoxSize()));
        return true;
    }

    private boolean updatePackManagerFromFile() {
        return packManager.updateOwnedCards(new File("data/packs.txt"));
    }

    private void notWellFormatted() {
        System.out.println("Filter not well formatted! Type 'help' to get help on filters \n" +
                "or 'quit' to discard the selected cards \n" +
                "or 'add cards' to add the selected cards to the draft deck.");
    }
}
