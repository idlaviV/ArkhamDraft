package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.SettingsDialog;

public class OpenSettingsButtonWorker extends AbstractButtonWorker{
    private final SettingsDialog settingsDialog;
    public OpenSettingsButtonWorker(Brain brain, SettingsDialog settingsDialog) {
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
