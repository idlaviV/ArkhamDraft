package arkhamDraft;

import java.util.HashMap;
import java.util.Map;

public class MasterCardBox extends CardBox {

    Map<String, Card> codeToCardMap = new HashMap<>();

    public MasterCardBox(Card[] cards) {
        super(cards);
        for (Card card : cards) {
            codeToCardMap.put(card.getCode(), card);
        }
    }

    public Card findCardByCode(String code) {
        return codeToCardMap.get(code);
    }
}
