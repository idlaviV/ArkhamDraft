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
                    System.out.println("Got lost? Type 'help' for help!");
            }
        }
        System.out.println(String.format("%d cards added to draft deck.",drafter.getFilteredBoxSize()));
        drafter.addCards();
    }

    private void decryptFilter(String input) {
        String[] inputParts = input.split(Relator.relatorRegex);
        Matcher matcher = Pattern.compile(Relator.relatorRegex).matcher(input);
        matcher.find();
        String relatorString = matcher.group();
        if(Relator.isContainRelator(relatorString)) {
            //BiFunction<String[],String, Boolean> relator = bla();
        } else {
            BiFunction<Integer, Integer, Boolean> relator = Relator.getNumericalRelator(relatorString);
        }
    }

    private void notWellFormatted() {
        System.out.println("Filter not well formatted! Type 'help' to get help on filters \n" +
                "or 'quit' to discard the selected cards \n" +
                "or 'add cards' to add the selected cards to the draft deck.");
    }
}
