package arkhamDraft;

import java.util.function.Function;

public class CardFilter {
    private Function<Card, Boolean> filter;
    private String name;

    public CardFilter(Function<Card, Boolean> filter, String name) {
        this.filter = filter;
        this.name = name;
    }

    public boolean apply(Card card) {
        return filter.apply(card);
    }

    @Override
    public String toString() {
        return name;
    }
}
