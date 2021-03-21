package arkhamDraft.UI.workerPool;

import arkhamDraft.SettingsManager;

public class generateBlacklistButtonWorker extends AbstractButtonWorker{
    private final SettingsManager settingsManager;

    public generateBlacklistButtonWorker(SettingsManager settingsManager) {
        super(null);
        this.settingsManager = settingsManager;
    }

    @Override
    protected void update() {

    }

    @Override
    protected Boolean doInBackground() throws Exception {
        settingsManager.generateDefaultBlacklist();
        return true;
    }
}
