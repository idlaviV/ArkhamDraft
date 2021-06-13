package arkhamDraft;

import jdk.management.resource.ResourceRequestDeniedException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JSONDeckReaderTest {
    private JSONDeckReader reader;

    @Test
    public void readJSONFromID() {
        givenIDWithoutCardBox();
        List<String> cards = reader.getCodes();
        assertEquals(39, cards.size());
    }

    @Test(expected = ResourceRequestDeniedException.class)
    public void readJSONFromIllegalID() {
        givenIllegalIDWithoutCardBox();
        List<String> cards = reader.getCodes();
        System.out.println(cards);
    }

    private void givenIDWithoutCardBox() {
        reader = new JSONDeckReader("1409343", null);
    }

    private void givenIllegalIDWithoutCardBox() {
        reader = new JSONDeckReader("1409344", null);
    }

}