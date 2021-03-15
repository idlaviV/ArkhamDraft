package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

public class AddFromSideBoardButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList sideboardList;
    private final Runnable updatePanels;

    public AddFromSideBoardButtonWorker(Brain brain, CardCheckBoxList sideboardList, Runnable updatePanels) {
        super(brain);
        this.sideboardList = sideboardList;
        this.updatePanels = updatePanels;
    }

    @Override
    protected Boolean doInBackground() {
        brain.guiAddFromSideboard(sideboardList.getCheckedCards());
        return true;
    }

    @Override
    protected void update() {
        updatePanels.run();
    }
}
