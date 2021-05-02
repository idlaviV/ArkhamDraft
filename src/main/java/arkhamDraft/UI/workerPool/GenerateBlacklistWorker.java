package arkhamDraft.UI.workerPool;

import arkhamDraft.SettingsManager;

import java.awt.*;

public class GenerateBlacklistWorker extends AbstractWorker {
    private final SettingsManager settingsManager;

    public GenerateBlacklistWorker(SettingsManager settingsManager, Component parent) {
        super(null, false, parent);
        this.settingsManager = settingsManager;
    }

    @Override
    protected void update() {

    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        settingsManager.generateDefaultBlacklist();
        return true;
    }
}
