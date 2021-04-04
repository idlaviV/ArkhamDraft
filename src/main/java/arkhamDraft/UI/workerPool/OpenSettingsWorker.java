package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.SettingsDialog;

public class OpenSettingsWorker extends AbstractWorker {
    private final SettingsDialog settingsDialog;
    public OpenSettingsWorker(Brain brain, SettingsDialog settingsDialog) {
        super(brain);
        this.settingsDialog = settingsDialog;
    }

    @Override
    protected void update() {
        settingsDialog.setVisible(true);
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        settingsDialog.openDialog();
        return true;
    }
}
