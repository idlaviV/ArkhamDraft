package arkhamDraft;


import java.util.function.BiFunction;
import java.util.function.Function;

public class CardBox {
    private Card[] cards;

    public static Function<Card, Boolean> generateCardFilter(String attribute, BiFunction<Integer,Integer,Boolean> relator, int value) {
        switch (attribute){
            case "xp":
                return (it) -> relator.apply(it.getXp(),value);
            default:
                return null;
        }
    }

    public CardBox(Card[] cards) {
        this.cards = cards;
    }

    public Card getCard(int position) {
        return cards[position];
    }
}
