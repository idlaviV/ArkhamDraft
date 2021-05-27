package arkhamDraft.UI.workerPool;

import arkhamDraft.SettingsManager;
import arkhamDraft.UI.SettingsDialog;

import javax.swing.*;
import java.awt.*;

public class UpdateDatabaseWorker extends AbstractWorker{
    private final SettingsManager mng;

    public UpdateDatabaseWorker(SettingsManager settingsManager, Component parent) {
        super(null, false, parent);
        mng = settingsManager;
    }

    @Override
    protected void update() {
        JOptionPane.showMessageDialog(parent, "Database updated. Please restart ArkhamDraft.");
    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        mng.updateDatabaseFromAPI();
        return true;
    }
}
