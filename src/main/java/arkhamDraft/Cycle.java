package arkhamDraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cycle cycle = (Cycle) o;

        if (index != cycle.getIndex()) return false;
        if (!Objects.equals(name, cycle.getName())) return false;
        return Objects.equals(packs, cycle.getPacks());
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (packs != null ? packs.hashCode() : 0);
        return result;
    }
}
