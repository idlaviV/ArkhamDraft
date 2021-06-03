package arkhamDraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CardBox {
    private Set<Card> cards = new HashSet<>();

    private final ArrayList<CardFilter> generatingFilters = new ArrayList<>();

    public CardBox(Card[] cards){
        this.cards = new HashSet<>(Arrays.asList(cards));
    }

    public CardBox(CardBox cardBox) {
        this.cards = new HashSet<>(cardBox.getCards());
        this.generatingFilters.addAll(cardBox.getGeneratingFilters());
    }

    public CardBox(Set<Card> cards) {
        this.cards = cards;
    }

    public CardBox(File fileDeck, CardBox masterCardBox) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileDeck));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.equals("") && Character.isDigit(line.charAt(0))) {
                cards.addAll(Decoder.findCardInCardBoxFromString(masterCardBox, line));
            }
        }
    }

    public void filter(CardFilter cardFilter) {
        generatingFilters.add(cardFilter);
        cards = cards.stream().filter(cardFilter::apply).collect(Collectors.toSet());
    }

    public Set<Card> getCards() {
        return cards;
    }

    public boolean containsCard(Card cardSearch) {
        return cards.stream().anyMatch(c -> c.equals(cardSearch));
    }

    public Optional<Card> searchForCode(String code) {
        return cards.stream().findFirst().filter(card -> code.equals(card.getCode()));
    }

    public List<Card> findAllCardsWithGivenTraits(String wholeName, String subName, int xp, int cardinality){
        List<Card> physicalCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getReal_name().equals(wholeName) && (card.getSubname() == null || card.getSubname().equals(subName)) && (card.getXp() == null ||card.getXp() == xp)) {
                for (int j=0; j < cardinality; j++) {
                    physicalCards.add(card.getPhysicalCard());
                }
            }
        }
        return physicalCards;
    }

    public ArrayList<CardFilter> getGeneratingFilters() {
        return generatingFilters;
    }
}
