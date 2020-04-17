package arkhamDraft;


import javax.swing.*;
import java.io.File;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        /*ArrayList<String> strings = new ArrayList<>();
        strings.add("xp<=4");
        strings.add("xp<==4");
        strings.add("xp>=4");
        strings.add("xp=4");
        strings.add("xp!=4");
        for (String string: strings) {
            System.out.println(String.format("[%s]-->[%s]", string, getRelator(string)));
        }*/


        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        File directoryDecks = new File("data/decks");
        if (!directoryDecks.exists()) {
            directoryDecks.mkdir();
        }
        File packsFile = new File("data/packs.json");
        File cardsFile = new File("data/cards.json");
        File settingsFile = new File("data/packs.txt");
        SettingsManager settingsManager = new SettingsManager();
        if (!(packsFile.exists() || cardsFile.exists())) {
            settingsManager.updateDatabaseFromAPI();
        }
        if (!settingsFile.exists()) {
            SettingsManager.generateDefaultSettings();
        }
        Brain brain = new Brain(settingsManager);
        brain.updateFromJson();
        settingsManager.updateSettings(settingsFile);

        /*File deckFile = new File("data/deck.txt"); // This part reads the deck in "data/deck" and prints it
        Deck deck = new Deck(deckFile, face.getMasterCardBox());
        ArrayList<String> output = deck.getPrintInfo();
        for (String line: output) {
            System.out.println(line);
        }*/

        brain.watch();
        System.out.println("finished");
    }

    public static String getRelator(String input) {

        return input.replaceFirst(".*(?=[<|>|!|[(?<!!)=]])","").replaceFirst(
                "(?<=[=|[<(?!=)]|[>(?!=)]]).*","").trim();
    }
}
