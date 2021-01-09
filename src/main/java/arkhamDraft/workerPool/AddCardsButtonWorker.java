package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.HintTextField;

import java.util.function.Function;

public class AddCardsButtonWorker extends AbstractButtonWorker{
    private final HintTextField valueSelector;
    private final Runnable dispose;
    private final Function<Boolean, Void> addCards;

    public AddCardsButtonWorker(Brain brain, HintTextField valueSelector, Runnable dispose, Function<Boolean, Void> addCards) {
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
        addCards.apply(true);
    }

    @Override
    protected Boolean doInBackground() {
        System.out.println("doInBackground");
        brain.guiLeavesFilterCardsDialog();
        return true;
    }
}
