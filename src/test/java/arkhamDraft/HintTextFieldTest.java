package arkhamDraft;
import arkhamDraft.HintTextField;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


import javax.swing.*;
import java.awt.*;

import static org.mockito.Mockito.when;


public class HintTextFieldTest {

    @Test
    public void test() {
        Assert.assertTrue(true);
    }

    @Test
    public void resetToHint_SetsTexTofFieldToHint() {
        String hintText = "hintText";
        //Graphics mockGraphics = new DebugGraphics();
        Graphics mockGraphics = Mockito.mock(Graphics2D.class);
        when(mockGraphics.getFontMetrics()).thenReturn(Mockito.mock(FontMetrics.class));
        HintTextField testField = new HintTextField(hintText,1);
        testField.setText("userInput");
        testField.setGraphics(mockGraphics);
        testField.resetToHint();
        Assert.assertEquals(hintText,testField.getText());

    }


}

