package arkhamDraft;

import java.util.ArrayList;
import java.util.List;

public class Cycle {
    private final int index;
    private final String name;
    private final List<Pack> packs;

    public Cycle(int index, String name, List<Pack> packs) {
        this.index = index;
        this.name = name;
        this.packs = packs;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public List<Pack> getPacks() {
        return packs;
    }

    @Override
    public String toString() {
        return "Cycle " +
                name + '[' +
                index + ']';
    }
}
