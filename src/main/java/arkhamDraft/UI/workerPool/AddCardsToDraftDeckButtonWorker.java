package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.HintTextField;

import javax.swing.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class AddCardsToDraftDeckButtonWorker extends AbstractButtonWorker{
    private final HintTextField valueSelector;
    private final Runnable dispose;
    private final Supplier<SwingWorker<Integer, Void>> addCards;

    public AddCardsToDraftDeckButtonWorker(Brain brain, HintTextField valueSelector, Runnable dispose, Supplier<SwingWorker<Integer, Void>> addCards) {
        super(brain);
        this.valueSelector = valueSelector;
        this.dispose = dispose;
        this.addCards = addCards;
    }

    @Override
    protected void update() {
        System.out.println("update");
        valueSelector.resetToHint();
        System.out.println("Now dispose");
        dispose.run();
        addCards.get().run();
    }

    @Override
    protected Boolean doInBackground() {
        System.out.println("doInBackground");
        brain.guiLeavesFilterCardsDialog();
        return true;
    }
}
