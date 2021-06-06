package arkhamDraft;


import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ArkhamDraftBrainTest {
  private Brain testBrain;

  @Mock
  private SettingsManager manager;

  @Test
  public void readDeckFromArkhamDB() {
      givenInitializeBrain();

      whenReadDeckFromArkhamDBiD("1214048");

      thenReadDeckFromLongFile();
  }

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
  @Ignore
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

    private void whenReadDeckFromArkhamDBiD(String id) {
        testBrain.loadDeckFromArkhamDBiD(id);
    }

    private void whenReadDeckFromFileMulticlass() {
        testBrain.buildDeckFromFile(new File("src/test/testdecks/testDeckMulticlass.txt"));
    }

    private void whenReadDeckFromFile() {
        testBrain.buildDeckFromFile(new File("src/test/testdecks/testDeckOneCard.txt"));
    }

    private void whenReadDeckFromLongFile() {
        testBrain.buildDeckFromFile(new File("src/test/testdecks/testDeckLong.txt"));
    }

    private void whenReadDeckFromEmptyFile() {
      testBrain.buildDeckFromFile(new File("src/test/testdecks/testDeckNoCard.txt"));
    }

    private void whenReadDeckFromFileWithErrors() {
        testBrain.buildDeckFromFile(new File("src/test/testdecks/testDeckWithErrors.txt"));
    }

    private void givenInitializeBrain() {
      SettingsManager manager = Mockito.mock(SettingsManager.class);
        try {
            Mockito.when(manager.updateDatabaseFromJSON()).thenReturn(new MasterCardBox(TestCommons.getSmallCardBase()));
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
