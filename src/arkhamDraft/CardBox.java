package arkhamDraft;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CardBox {
    private Set<Card> cards;

    public CardBox(Card[] cards){
        this.cards = new HashSet<>(Arrays.asList(cards));
    }

    public CardBox(CardBox cardBox) {
        this.cards = new HashSet<>(cardBox.getCards());
    }

    public void filter(Function<Card, Boolean> filter) {
        cards = cards.stream().filter(filter::apply).collect(Collectors.toSet());
    }

    public Set<Card> getCards() {
        return cards;
    }
}
