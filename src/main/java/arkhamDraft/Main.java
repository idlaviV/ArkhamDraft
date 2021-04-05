package arkhamDraft;


import arkhamDraft.UI.Face;

import javax.swing.*;
import java.io.File;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        File directoryDecks = new File("./data/decks");
        if (!directoryDecks.exists()) {
            directoryDecks.mkdir();
        }
        File packsFile = new File("./data/packs.json");
        File cardsFile = new File("./data/cards.json");
        File settingsFile = new File("./data/packs.txt");
        SettingsManager settingsManager = new SettingsManager();
        if (!(packsFile.exists() || cardsFile.exists())) {
            settingsManager.updateDatabaseFromAPI();
        }
        Brain brain = new ArkhamDraftBrain(settingsManager);
        Face face = new Face(brain);
        face.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        face.setTitle("ArkhamDraft");
        face.setSize(1500,800);
        face.setResizable(true);
        face.setLocation(50,50);
        face.setVisible(true);
        face.initComponents();
        brain.updateFromJson();
        if (!settingsFile.exists()) {
            settingsManager.generateDefaultSettings();
        }
        settingsManager.updateSettings();
    }

}
