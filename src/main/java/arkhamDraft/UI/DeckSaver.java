package arkhamDraft.UI;

import arkhamDraft.Brain;
import arkhamDraft.UI.workerPool.SaveDeckWorker;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DeckSaver {

    private DeckSaver() {
    }

    public static boolean saveDeck(JFileChooser fc, Component parent, Brain brain) {
        File directory = new File("./data/decks");
        if (!directory.exists()) {
            directory.mkdir();
        }
        fc.setCurrentDirectory(directory);

        int returnVal = fc.showSaveDialog(parent);
        boolean approved = (returnVal == JFileChooser.APPROVE_OPTION);
        if (approved) {
            File file = fc.getSelectedFile();
            String pathName = file.toPath().toString();
            if (!pathName.endsWith(".txt")) {
                file = new File(pathName + ".txt");
            }
            new SaveDeckWorker(brain, parent, file).execute();
        }
        return approved;
    }
}
