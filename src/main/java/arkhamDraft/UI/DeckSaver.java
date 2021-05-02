package arkhamDraft.UI;

import arkhamDraft.Brain;
import arkhamDraft.UI.workerPool.SaveDeckWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveActionListener  {
    private final JFileChooser fc;
    private final Component parent;
    private final Brain brain;

    private SaveActionListener() {
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        File directory = new File("./data/decks");
        if (!directory.exists()) {
            directory.mkdir();
        }
        fc.setCurrentDirectory(directory);
        int returnVal = fc.showSaveDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String pathName = file.toPath().toString();
            if (!pathName.endsWith(".txt")) {
                file = new File(pathName + ".txt");
            }
            new SaveDeckWorker(brain, file).execute();
        }
    }
}
