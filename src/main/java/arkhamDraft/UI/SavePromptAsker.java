package arkhamDraft.UI;

import javax.swing.*;

public class SavePromptAsker {
    public static int SAVE_YES = 0;
    public static int SAVE_NO = 1;
    public static int CANCEL = 2;


    private SavePromptAsker() {
    }

    public static int promptUser() {
        Object[] options = {"Yes", "No", "Cancel"};
        return JOptionPane.showOptionDialog(null,"Your deck has unsaved changes. Do you want to save?",
                "Save Deck?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
    }

}
