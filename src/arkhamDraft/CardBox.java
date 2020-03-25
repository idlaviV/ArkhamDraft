package arkhamDraft;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CardBox {
    private Set<Card> cards;

    public CardBox(Card[] cards){
        this.cards = new HashSet<>(Arrays.asList(cards));
    }

    public CardBox(CardBox cardBox) {
        this.cards = new HashSet<>(cardBox.getCards());
    }

    public CardBox(Set<Card> cards) {
        this.cards = cards;
    }

    public void filter(CardFilter cardFilter) {
        cards = cards.stream().filter(cardFilter::apply).collect(Collectors.toSet());
    }

    public Set<Card> getCards() {
        return cards;
    }

    public List<Card> findCardFromString(String input) {
        Pattern pattern = Pattern.compile("x (.*?) \\(");
        Matcher matcher = pattern.matcher(input);
        String realInput = "";
        if (matcher.find()) {
            realInput = matcher.group(1);
        }
        String cardString = input.substring(0,input.indexOf("x"));
        int cardinality = Integer.parseInt(cardString);
        String wholeName = realInput;
        int xp = 0;
        String subname = null;
        if (wholeName.contains("[")) {
            xp = Integer.parseInt(wholeName.substring(wholeName.indexOf("[")+1,wholeName.indexOf("]")));
            wholeName = wholeName.substring(0,wholeName.indexOf("[")).trim();
        }
        if (wholeName.contains(":")) {
            subname = wholeName.substring(wholeName.indexOf(":")+1);
            wholeName = wholeName.substring(0, wholeName.indexOf(":"));
        }
        List<Card> physicalCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getReal_name().equals(wholeName) && (card.getSubname() == null || card.getSubname().equals(subname)) && (card.getXp() == null ||card.getXp() == xp)) {
                for (int j=0; j < cardinality; j++) {
                    physicalCards.add(card.getPhysicalCard());
                }
            }
        }
        return physicalCards;
    }
}
