package arkhamDraft;

import java.util.Scanner;

public class Face {
    private Drafter drafter;
    private CardBox masterCardBox;

    public Face(CardBox masterCardBox) {
        this.masterCardBox = masterCardBox;
    }

    public void watch(){
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        while (true) {
            String input = scanner.next();
            if (input.equals("quit")){
                break;
            }
            switch (input) {
                case "startDraft":
                    drafter = new Drafter(masterCardBox);
                case "help":
                    System.out.println("Useable commands are: 'startDraft', 'quit'.");
                default:
                    System.out.println("Need help? Type 'help'.");
            }
        }
        scanner.close();
    }
}
