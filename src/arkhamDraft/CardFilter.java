package arkhamDraft;

import java.util.function.Function;

public class CardFilter {
    private Function<Card, Boolean> filter;

    public CardFilter(Function<Card, Boolean> filter) {
        this.filter = filter;
    }

    public boolean apply(Card card) {
        return filter.apply(card);
    }
}
