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
        List<String> cards = reader.getIDs();
        assertEquals(39, cards.size());
    }

    private void givenIDWithoutCardBox() {
        reader = new JSONDeckReader("1409343", null);
    }

    private void givenIllegalIDWithoutCardBox() {
        reader = new JSONDeckReader("000", null);
    }

}