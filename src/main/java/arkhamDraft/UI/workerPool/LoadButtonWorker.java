package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class LoadButtonWorker extends AbstractWorker{
    private final Runnable updateDeckPanel;
    private final Runnable enableDeckComponents;
    private final Runnable updateDraftingAndSideboardPanel;

    public LoadButtonWorker(Brain brain, Component parent, Runnable updateDeckPanel, Runnable enableDeckComponents, Runnable updateDraftingAndSideboardPanel) {
        super(brain, true, parent);
        this.updateDeckPanel = updateDeckPanel;
        this.enableDeckComponents = enableDeckComponents;
        this.updateDraftingAndSideboardPanel = updateDraftingAndSideboardPanel;
    }

    @Override
    protected void update() {

    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        File directory = new File("./data/decks");
        JFileChooser fc = new JFileChooser();
        if (!directory.exists()) {
            directory.mkdir();
        }
        fc.setCurrentDirectory(directory);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "Text Files (*.txt)";
            }
        });
        int returnVal = fc.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            new LoadDeckWorker(
                    brain,
                    parent,
                    file,
                    updateDeckPanel,
                    enableDeckComponents,
                    updateDraftingAndSideboardPanel
            ).execute();
        }
        return true;
    }
}
