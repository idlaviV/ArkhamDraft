package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

import java.awt.*;

public class AddFromSideBoardWorker extends AbstractWorker {
    private final CardCheckBoxList sideboardList;
    private final Runnable updatePanels;

    public AddFromSideBoardWorker(Brain brain, Component parent, CardCheckBoxList sideboardList, Runnable updatePanels) {
        super(brain, false, parent);
        this.sideboardList = sideboardList;
        this.updatePanels = updatePanels;
    }

    @Override
    protected Boolean backgroundTask() {
        brain.guiAddFromSideboard(sideboardList.getCheckedCards());
        return true;
    }

    @Override
    protected void update() {
        updatePanels.run();
    }
}
