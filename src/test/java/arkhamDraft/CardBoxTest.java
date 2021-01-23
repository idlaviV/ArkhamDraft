package arkhamDraft;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CardBoxTest {

    private CardBox testBox;

    @Test
    public void filterCardBoxWithoutNonGuardianByNonGuardian_ResultsInEmptyCardBox() {
        givenCardBoxWithGuardianCards();

        whenFilterGetRidOfFaction("GUARDIAN");

        assertTrue(testBox.getCards().isEmpty());
    }

    @Test
    public void filterCardBoxByNonGuardian_ResultsInCardBoxWithoutGuardianCardsButNotEmpty() {
        givenCardBoxWithGuardianAndOneSeekerCards();

        whenFilterGetRidOfFaction("GUARDIAN");

        assertEquals(1, testBox.getCards().size());
    }

    @Test
    public void filterCardBoxByFactionNotContained_ResultsInNoChange() {
        givenCardBoxWithGuardianAndOneSeekerCards();

        whenFilterGetRidOfFaction("ROGUE");

        assertEquals(2, testBox.getCards().size());
    }

    @Test
    public void filterCardBoxByNonExistingFaction_ResultsInNoChange() {
        givenCardBoxWithGuardianAndOneSeekerCards();

        whenFilterGetRidOfFaction("ROGUEEEEE");

        assertEquals(2, testBox.getCards().size());
    }

    @Test
    public void filterCardBoxByXpOnEmptyBox_ResultsInEmptyBox() {
        testBox = new CardBox(new HashSet<>());

        whenFilterXP(">",2);

        assertTrue(testBox.getCards().isEmpty());
    }

    @Test
    public void filterCardBoxByXpOnRainbowBoxEquals_ResultsInOneCard() {
        givenCardBoxWithRainbowXP();

        whenFilterXP("=",2);

        assertEquals(testBox.getCards().size(),1);
    }

    @Test
    public void filterCardBoxByXpOnRainbowBoxGreater_ResultsInOneCard() {
        givenCardBoxWithRainbowXP();

        whenFilterXP(">",2);

        assertEquals(testBox.getCards().size(),3);
    }

    @Test
    public void filterCardBoxByXpOnRainbowBoxGreaterEqual_ResultsInOneCard() {
        givenCardBoxWithRainbowXP();

        whenFilterXP(">=",2);

        assertEquals(testBox.getCards().size(),4);
    }

    @Test
    public void filterCardBoxByXpOnRainbowBoxGreaterEqualInvalidNumber_ResultsInOneCard() {
        givenCardBoxWithRainbowXP();

        whenFilterXP(">=",-2);

        assertEquals(testBox.getCards().size(),6);
    }


    private void givenCardBoxWithGuardianCards() {
        Card[] setOfCards = new Card[]{
                getDummyCardWithFaction("GUARDIAN"),
                getDummyCardWithFaction("GUARDIAN")};
        testBox = new CardBox(setOfCards);
    }

    private void givenCardBoxWithGuardianAndOneSeekerCards() {
        Card[] setOfCards = new Card[]{
                getDummyCardWithFaction("GUARDIAN"),
                getDummyCardWithFaction("SEEKER")
        };
        testBox = new CardBox(setOfCards);
    }

    private void givenCardBoxWithRainbowXP() {
        Card[] setOfCards = new Card[6];
        for (int i =0; i<=5; i++) {
            setOfCards[i] = getDummyCardWithXP(i);
        }
        testBox = new CardBox(setOfCards);
    }

    private void whenFilterGetRidOfFaction(String factionName) {
        testBox.filter(Card.generateCardFilter(
                "FACTION",
                Relator.getContainRelator("!:"),
                Collections.singletonList(factionName),
                "contains"));
    }

    private void whenFilterXP(String relatorString, int value) {
        testBox.filter(Card.generateCardFilter("XP", Relator.getNumericalRelator(relatorString),value,relatorString));
    }

    private Card getDummyCardWithFaction(String factionName) {
        return new Card(null, null, factionName, null, false, null, null, null, null, null);
    }

    private Card getDummyCardWithXP(int xpValue) {
        return new Card(null, null, null, null, false, null, null, null, null, xpValue);
    }
}
