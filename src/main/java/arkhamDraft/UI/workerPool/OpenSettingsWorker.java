package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.SettingsDialog;

import java.awt.*;

public class OpenSettingsWorker extends AbstractWorker {
    private final SettingsDialog settingsDialog;
    public OpenSettingsWorker(Brain brain, SettingsDialog settingsDialog) {
        super(brain, false, null);
        this.settingsDialog = settingsDialog;
    }

    @Override
    protected void update() {
        settingsDialog.setVisible(true);
    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        settingsDialog.openDialog();
        return true;
    }
}
