package arkhamDraft.UI.workerPool;

import arkhamDraft.Pack;
import arkhamDraft.SettingsManager;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ApplySettingsWorker extends AbstractWorker {
    private final SettingsManager manager;
    private final boolean regularCards;
    private final boolean secondCore;
    private final TreePath[] checkedPaths;
    private final Consumer<Boolean> changesWereMaid;


    public ApplySettingsWorker(SettingsManager manager, boolean regularCards, boolean secondCore, TreePath[] checkedPaths, Consumer<Boolean> changesWereMaid) {
        super(null, false, null);
        this.manager = manager;
        this.regularCards = regularCards;
        this.secondCore = secondCore;
        this.checkedPaths = checkedPaths;
        this.changesWereMaid = changesWereMaid;
    }

    @Override
    protected void update() {
        changesWereMaid.accept(false);
    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        manager.toggleRegular(regularCards);
        manager.toggleSecondCore(secondCore);
        updateOwnedPacks();
        manager.saveSettingsToFile();
        return true;
    }

    private void updateOwnedPacks() {
        List<Pack> ownedPacks = new ArrayList<>();
        for (TreePath path : checkedPaths) {
            DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (lastNode.getUserObject() instanceof Pack) {
                Pack pack = (Pack) lastNode.getUserObject();
                ownedPacks.add(pack);
            }
        }
        manager.setOwnedPacks(ownedPacks);
    }
}
