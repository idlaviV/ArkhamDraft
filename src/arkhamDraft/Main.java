package arkhamDraft;


import java.io.File;

import java.io.IOException;




public class Main {

    public static void main(String[] args) throws IOException {

        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
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
        Face face = new Face(settingsManager);
        face.updateFromJson();
        settingsManager.updateSettings(settingsFile);

        /*File deckFile = new File("data/deck.txt"); // This part reads the deck in "data/deck" and prints it
        Deck deck = new Deck(deckFile, face.getMasterCardBox());
        for (Card card: deck.getCards()) {
            System.out.println(card.getFactionColor() + card.getDraftInfo() + Face.ANSI_RESET);
        }*/

        face.watch();
        System.out.println("finished");
    }
}
