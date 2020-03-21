package arkhamDraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CardBox {
    private List<Card> cards;

    public CardBox(List<Card> cards) {
        this.cards = cards;
    }

    public CardBox(Card[] cards){
        this.cards = Arrays.asList(cards);
    }

    public CardBox(CardBox cardBox) {
        this.cards=new ArrayList<>(cardBox.getCards());
    }

    public void filter(Function<Card, Boolean> filter) {
        cards = cards.stream().filter(filter::apply).collect(Collectors.toList());
    }

    public Card getCard(int position) {
        return cards.get(position);
    }

    public List<Card> getCards() {
        return cards;
    }
}
