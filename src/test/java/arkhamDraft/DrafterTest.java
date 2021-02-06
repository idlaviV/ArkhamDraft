package arkhamDraft;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class DrafterTest {
    private CardBox ownedCards;
    private Drafter drafter;

    @Before
    public void setup() {
        givenInitializedDrafter();
    }

    @Test
    public void filterDraftingDeckByFactionAndFinalize() {

        whenFilterDraftingDeckByFaction();

        thenNumberOfCardsInDecksIsAsExpected(2,4);
    }

    @Test
    public void filterDraftingDeckByFactionAndFinalizeAgainstSingletonDeck() {
        givenEmptyVessel();

        whenFilterDraftingDeckByFaction();

        thenNumberOfCardsInDecksIsAsExpected(2,3);
    }

    @Test
    public void filterDraftingDeckByFactionAndFinalizeAgainstGuardianDeckWithDoubleCard() {
        givenEmptyVessel();
        givenEmptyVessel();

        whenFilterDraftingDeckByFaction();

        thenNumberOfCardsInDecksIsAsExpected(2,2);
    }



    private void givenInitializedDrafter() {
        givenOwnedCardsSmallCardDataBase();
        drafter = new Drafter(ownedCards, true);
        drafter.initializeCardAddition();
    }

    private void givenEmptyVessel() {
        drafter.addCardsToDeck(Collections.singletonList(TestCommons.getCardEmptyVessel()));
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
        drafter.finalizeDraft();
    }

    private void thenNumberOfCardsInDecksIsAsExpected(int expectedDraftingBoxSize, int expectedPhysicalDraftingBoxSize) {
        assertEquals(expectedDraftingBoxSize,drafter.getDraftingBoxSize());
        assertEquals(expectedPhysicalDraftingBoxSize,drafter.getPhysicalDraftingBoxSize());
    }





}
