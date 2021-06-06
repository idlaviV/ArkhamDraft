package arkhamDraft;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class DrafterTest {
    private CardBox ownedCards;
    private Drafter drafter;

    @Before
    public void setup() {
        givenInitializedDrafter();
    }

    @Test
    public void filterDraftingDeckByFactionGuardian() {

        whenFilterDraftingDeckByFaction();

        thenNumberOfCardsInDraftingBoxIsAsExpected(2);
    }

    @Test
    public void filterDraftingDeckByXP() {

        whenFilterDraftingDeckByXP();

        thenNumberOfCardsInDraftingBoxIsAsExpected(16);
    }


    private void givenInitializedDrafter() {
        givenOwnedCardsSmallCardDataBase();
        drafter = new Drafter(ownedCards, true);
        drafter.initializeCardAddition();
    }

    private void givenOwnedCardsSmallCardDataBase() {
        ownedCards = new CardBox(TestCommons.getSmallCardBase());
    }

    private void whenFilterDraftingDeckByFaction() {
        drafter.filter(Card.generateCardFilter(
                "faction",
                Relator.getContainRelator(":"),
                Collections.singletonList("guardian"),
                "contains"));
        drafter.addCards();
    }

    private void whenFilterDraftingDeckByXP() {
        drafter.filter(Card.generateCardFilter(
                "XP",
                Relator.getNumericalRelator("<"),
                2,
                "smaller"));
        drafter.addCards();
    }

    private void thenNumberOfCardsInDraftingBoxIsAsExpected(int expected) {
        assertEquals(expected, drafter.getDraftingBoxSize());
    }





}
