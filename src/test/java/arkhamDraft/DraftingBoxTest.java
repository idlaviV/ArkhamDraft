package arkhamDraft;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DraftingBoxTest {

    private DraftingBox testBox;
    private ArrayList<Card> draftedCards;
    private Deck testDeck;
    private boolean secondCore = false;

    @Before
    public void setup(){
        testBox = new DraftingBox();
        testDeck = new Deck();
    }

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
        Card[] cards = new Card[]{
                TestCommons.getDummyCardWithName("TestCard1", "0"),
                TestCommons.getDummyCardWithName("TestCard2", "1")
        };
        CardBox cardBox = new CardBox(cards);
        testBox.addCards(cardBox);
        testBox.finalizeDraft(true, new Deck());
    }


    private void givenInitializeEmptyCardBox() {
        testBox.finalizeDraft(true, new Deck());
    }

    @Test
    public void finalizeDraftWithFearlessAgainstEmptyDeck() {
        givenDraftingBoxWithFearless();

        whenDraftingBoxIsFinalized();

        thenNumberOfFearlessInPhysicalDeckIs(2);
    }


    @Test
    public void finalizeDraftWithFearlessAgainstSingletonFearlessDeck() {
        givenDraftingBoxWithFearless();
        givenAddFearlessToTestDeck();

        whenDraftingBoxIsFinalized();

        thenNumberOfFearlessInPhysicalDeckIs(1);
    }

    @Test
    public void finalizeDraftWithFearlessAgainstDoubleFearlessDeck() {
        givenDraftingBoxWithFearless();
        givenAddFearlessToTestDeck();
        givenAddFearlessToTestDeck();

        whenDraftingBoxIsFinalized();

        thenNumberOfFearlessInPhysicalDeckIs(0);
    }

    @Test
    public void finalizeDraftWithOpenGateAgainstEmptyDeck() {
        givenDraftingBoxWithOpenGate();

        whenDraftingBoxIsFinalized();

        thenNumberOfOpenGateInPhysicalDeckIs(2);
    }

    @Test
    public void finalizeDraftWithMilanAgainstEmptyDeck() {
        givenDraftingBoxWithMilan();
        givenNoSecondCore();

        whenDraftingBoxIsFinalized();

        thenNumberOfMilanInPhysicalDeckIs(1);
    }

    private void givenNoSecondCore() {
        secondCore = false;
    }

    private void givenAddFearlessToTestDeck() {
        testDeck.addCard(TestCommons.getCardFearless());
    }

    private void whenDraftingBoxIsFinalized() {
        testBox.finalizeDraft(secondCore, testDeck);
    }

    private void thenNumberOfFearlessInPhysicalDeckIs(int expected) {
        assertEquals(expected,Collections.frequency(testBox.getPhysicalDraftingBox(), TestCommons.getCardFearless()));
    }

    private void thenNumberOfOpenGateInPhysicalDeckIs(int expected) {
        assertEquals(expected,Collections.frequency(testBox.getPhysicalDraftingBox(), TestCommons.getCardOpenGate()));
    }

    private void thenNumberOfMilanInPhysicalDeckIs(int expected) {
        assertEquals(expected,Collections.frequency(testBox.getPhysicalDraftingBox(), TestCommons.getCardMilan()));
    }

    private void givenDraftingBoxWithFearless() {
        testBox.addCards(new CardBox(
                new Card[]{
                        TestCommons.getCardFearless()
                })
        );
    }

    private void givenDraftingBoxWithOpenGate() {
        testBox.addCards(new CardBox(
                new Card[]{
                        TestCommons.getCardOpenGate()
                })
        );
    }

    private void givenDraftingBoxWithMilan() {
        testBox.addCards(new CardBox(
                new Card[]{
                        TestCommons.getCardMilan()
                })
        );
    }
}