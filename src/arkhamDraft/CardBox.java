package arkhamDraft;

import java.util.*;
import java.util.stream.Collectors;

public class CardBox {
    private Set<Card> cards;

    public CardBox(Card[] cards){
        this.cards = new HashSet<>(Arrays.asList(cards));
    }

    public CardBox(CardBox cardBox) {
        this.cards = new HashSet<>(cardBox.getCards());
    }

    public void filter(CardFilter cardFilter) {
        cards = cards.stream().filter(cardFilter::apply).collect(Collectors.toSet());
    }

    public Set<Card> getCards() {
        return cards;
    }
}
