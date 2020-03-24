package arkhamDraft;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;


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
        face.watch();
        System.out.println("finished");
    }
}
