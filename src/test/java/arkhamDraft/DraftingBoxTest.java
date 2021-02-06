package arkhamDraft;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DraftingBoxTest {

    private DraftingBox testBox;
    private ArrayList<Card> draftedCards;

    @Test
    public void draftCardsFromEmptyCardBox() {
        givenInitializeEmptyCardBox();

        draftedCards = testBox.draftCards(1);

        assertTrue(draftedCards.isEmpty());
        assertEquals(0,testBox.getPhysicalDraftingBoxSize());
    }

    @Test
    public void draftCardFromNonEmptyCardBox() {
        givenInitializeCardBoxWithFourCards();

        draftedCards = testBox.draftCards(2);

        assertEquals(2, draftedCards.size());
        assertEquals(2, testBox.getPhysicalDraftingBoxSize());
    }

    @Test
    public void draftTooManyCardsFromNonEmptyCardBox() {
        givenInitializeCardBoxWithFourCards();

        draftedCards = testBox.draftCards(5);

        assertEquals(0, draftedCards.size());
        assertEquals(4, testBox.getPhysicalDraftingBoxSize());
    }

    private void givenInitializeCardBoxWithFourCards() {
        testBox = new DraftingBox();
        Card[] cards = new Card[]{
                TestCommons.getDummyCardWithName("TestCard1"),
                TestCommons.getDummyCardWithName("TestCard2")
        };
        CardBox cardBox = new CardBox(cards);
        testBox.addCards(cardBox);
        testBox.finalizeDraft(true, new Deck());
    }


    private void givenInitializeEmptyCardBox() {
        testBox = new DraftingBox();
        testBox.finalizeDraft(true, new Deck());
    }
}