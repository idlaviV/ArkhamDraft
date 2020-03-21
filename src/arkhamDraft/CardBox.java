package arkhamDraft;


import java.util.List;
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

    public static Function<Card, Boolean> generateCardFilter(String attribute, BiFunction<List<Faction>, Faction, Boolean> relator,
                                                             Faction value) {
        if (attribute.equals("faction")) {
            return (it) -> relator.apply(it.getFaction_code(),value);
        } else {
            return null;
        }
    }

    public static Function<Card, Boolean> generateCardFilter(String attribute, BiFunction<List<String>, String, Boolean> relator,
                                                             String value) {
        switch (attribute){
            case "trait":
                return (it) -> relator.apply(it.getTraits(),value);
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
