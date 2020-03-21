package arkhamDraft;

import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Face {
    private Drafter drafter;
    private CardBox masterCardBox;

    public Face(CardBox masterCardBox) {
        this.masterCardBox = masterCardBox;
    }

    public void watch(){
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        boolean quit = false;
        while (!quit) {
            String input = scanner.next();
            switch (input) {
                case "quit":
                    quit = true;
                    break;
                case "start draft":
                    drafter = new Drafter(masterCardBox);
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
                case "help":
                    System.out.println("Usable commands are: 'start draft', 'increase draft deck','quit'.");
                    break;
                default:
                    System.out.println("Need help? Type 'help'.");
            }
        }
        scanner.close();
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
            drafter.filter(Card.generateCardFilter(inputParts[0],
                    Relator.getContainRelator(relatorString),
                    inputParts[1]));
        }
        System.out.println(String.format("%d cards left.",drafter.getFilteredBoxSize()));
        return true;
    }

    private void notWellFormatted() {
        System.out.println("Filter not well formatted! Type 'help' to get help on filters \n" +
                "or 'quit' to discard the selected cards \n" +
                "or 'add cards' to add the selected cards to the draft deck.");
    }
}
