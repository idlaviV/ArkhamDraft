package arkhamDraft;


import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.io.File;
import java.io.IOException;


import static org.junit.Assert.*;

public class ArkhamDraftBrainTest {
  private Brain testBrain;

  @Mock
  private SettingsManager manager;


  @Test
    public void readDeckFromFile() {
      givenInitializeBrain();

      whenReadDeckFromFile();

      thenReadDeckFromFIle();

  }

  @Test
  public void readDeckFromEmptyFile() {
      givenInitializeBrain();

      whenReadDeckFromEmptyFile();

      thenReadDeckFromEmptyFile();
  }

  @Test
  public void readDeckFromFileWithErrors() {
      givenInitializeBrain();

      whenReadDeckFromFileWithErrors();

      thenReadDeckFromFIleWithErrors();
  }

  @Test
  public void readDeckFromLongFile() {
      givenInitializeBrain();

      whenReadDeckFromLongFile();

      thenReadDeckFromLongFile();
  }

  @Test
  public void readDeckFromFileMulticlass() {
      givenInitializeBrain();

      whenReadDeckFromFileMulticlass();

      thenReadDeckFromFileMulticlass();
  }

    private void thenReadDeckFromFileMulticlass() {
        assertEquals(4,testBrain.getDraftedDeck().getSize());//TODO:Doesn't work
    }

    private void thenReadDeckFromLongFile() {
        assertEquals(41,testBrain.getDraftedDeck().getSize());//%TODO:Doesn't work since campaign cards are not read
    }

    private void thenReadDeckFromFIleWithErrors() {
        assertEquals(2, testBrain.getDraftedDeck().getSize());
    }

    private void thenReadDeckFromEmptyFile() {
        assertEquals(0, testBrain.getDraftedDeck().getSize());
    }


    private void thenReadDeckFromFIle() {
        assertEquals(1, testBrain.getDraftedDeck().getSize());
    }

    private void whenReadDeckFromFileMulticlass() {
        testBrain.buildDeckFromFile(new File("data/decks/testDeckMulticlass.txt"));
    }

    private void whenReadDeckFromFile() {
        testBrain.buildDeckFromFile(new File("data/decks/testDeckOneCard.txt"));
    }

    private void whenReadDeckFromLongFile() {
        testBrain.buildDeckFromFile(new File("data/decks/testDeckLong.txt"));
    }

    private void whenReadDeckFromEmptyFile() {
      testBrain.buildDeckFromFile(new File("data/decks/testDeckNoCard.txt"));
    }

    private void whenReadDeckFromFileWithErrors() {
        testBrain.buildDeckFromFile(new File("data/decks/testDeckWithErrors.txt"));
    }

    private void givenInitializeBrain() {
      SettingsManager manager = Mockito.mock(SettingsManager.class);
        try {
            Mockito.when(manager.updateDatabaseFromJSON()).thenReturn(new CardBox(TestCommons.getSmallCardBase()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        testBrain = new ArkhamDraftBrain(manager);
        try {
            testBrain.updateFromJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        manager.updateSettings();
        testBrain.guiOpensNewDraftDeckDialog();
    }
}
