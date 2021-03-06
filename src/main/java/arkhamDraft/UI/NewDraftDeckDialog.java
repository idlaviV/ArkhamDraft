package arkhamDraft.UI;

import arkhamDraft.Brain;
import arkhamDraft.CardFilter;
import arkhamDraft.UI.workerPool.DeleteWorker;
import arkhamDraft.UI.workerPool.FinalizeDraftDeckWorker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class NewDraftDeckDialog extends JDialog {
    private final Brain brain;
    private JTextArea draftDeckLog;
    //private JLabel draftDeckSizeLabel;
    private FilterCardsDialog filterCardsDialog;
    private final JFileChooser fc = new JFileChooser();
    private final Runnable updateAllPanels;
    private final Runnable enableDraft;
    private final String cardFilterDirectory = "./data/cardFilter";

    public NewDraftDeckDialog(Brain brain, Runnable updateAllPanels, Runnable enableDraft) {
        super();
        this.brain = brain;
        this.updateAllPanels = updateAllPanels;
        this.enableDraft = enableDraft;
        initializeDialogue();
        //setVisible(true);
    }

    private void initializeDialogue() {
        setTitle("New Draft Deck");
        setSize(300,300);
        setLocation(100,100);
        setModal(true);
        setCloseOperation();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel currentDraftDeckPanel = new JPanel();
        currentDraftDeckPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(currentDraftDeckPanel);

        //draftDeckSizeLabel = new JLabel("Draft deck is empty");
        currentDraftDeckPanel.add(initializeDraftDeckLog());
        filterCardsDialog = new FilterCardsDialog(brain, this::addCards);
        add(initializeButtonPanel());


    }

    private void setCloseOperation() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private Component initializeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth=3;
        c.gridx=0; c.gridy=0; c.weightx = 1;
        buttonPanel.add(initializeAddCardsButton(), c);
        c.gridx=0; c.gridy=1; c.weightx = 1;
        buttonPanel.add(initializeFinalizeDraftDeckButton(), c);
        c.gridwidth=1;
        c.gridx=0; c.gridy=2; c.weightx = 0.5;
        buttonPanel.add(initializeSaveButton(), c);
        c.gridx=1; c.gridy=2; c.weightx = 0.5;
        buttonPanel.add(initializeLoadButton(), c);
        c.gridx=2; c.gridy=2; c.weightx = 0.5;
        buttonPanel.add(initializeDeleteButton(), c);
        return buttonPanel;
    }

    private JTextArea initializeDraftDeckLog() {
        draftDeckLog = new JTextArea("Created empty draft deck.", 5, 20);
        JScrollPane draftDeckLogScrollPane = new JScrollPane(draftDeckLog);
        draftDeckLogScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        draftDeckLog.setBackground(this.getBackground());
        return draftDeckLog;
    }

    private Component initializeAddCardsButton() {
        JButton addCardsButton = new JButton("Add more Cards");
        addCardsButton.addActionListener(e -> {
            filterCardsDialog.tidyUp();
            brain.guiEntersFilterCardsDialog();
            filterCardsDialog.setVisible(true);
        });
        return addCardsButton;
    }

    private SwingWorker<Integer, Void> addCards() {
        SwingWorker<Integer,Void> sw = new SwingWorker<Integer, Void>() {
            @Override
            protected Integer doInBackground() {
                setProgress(0);
                int preAdd = brain.getDraftingBoxSize();
                brain.addCards();
                return brain.getDraftingBoxSize() - preAdd;
            }

            @Override
            protected void done() {
                Integer amountOfAddedCards;
                try {
                    amountOfAddedCards = get();
                    if (amountOfAddedCards != null) {
                        updateDraftDeckLog(String.format("%d card(s) added to draft deck.", amountOfAddedCards));
                        updateDraftDeckSize(brain.getDraftingBoxSize());
                        setProgress(100);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        sw.execute();
        return sw;
    }


    private Component initializeFinalizeDraftDeckButton() {
        JButton finalizeDraftDeckButton = new JButton("Finalize");
        finalizeDraftDeckButton.addActionListener(e -> {
                    new FinalizeDraftDeckWorker(
                            brain,
                            this,
                            this::dispose,
                            updateAllPanels,
                            enableDraft
                    ).execute();
                });
        return finalizeDraftDeckButton;
    }

    private Component initializeSaveButton() {
        JButton saveButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/NewDraftDeckDialog/actions-document-save-icon.png"));
            saveButton.setIcon(new ImageIcon(img));
            saveButton.setMargin(new Insets(0, 0, 0, 0));
            saveButton.setBorder(null);
            saveButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveButton.addActionListener(e->{
            File directory = new File(cardFilterDirectory);
            if (!directory.exists()) {
                directory.mkdir();
            }
            fc.setCurrentDirectory(directory);
            int returnVal = fc.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String pathName = file.toPath().toString();
                if (!pathName.endsWith(".txt")) {
                    file = new File(pathName + ".txt");
                }
                brain.guiSaveFilterList(file);
            }
        });
        return saveButton;
    }

    private Component initializeLoadButton() {
        JButton loadButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/NewDraftDeckDialog/actions-document-open-folder-icon.png"));
            loadButton.setIcon(new ImageIcon(img));
            loadButton.setMargin(new Insets(0, 0, 0, 0));
            loadButton.setBorder(null);
            loadButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadButton.addActionListener(e -> {
            File directory = new File(cardFilterDirectory);
            if (!directory.exists()) {
                directory.mkdir();
            }
            fc.setCurrentDirectory(directory);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                }

                @Override
                public String getDescription() {
                    return "Text Files (*.txt)";
                }
            });
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                loadFilterList(file);
            }
        });
        return loadButton;
    }

    private void loadFilterList(File file) {
        Supplier<SwingWorker<Integer, Void>> addCards = this::addCards;
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                brain.loadFilterList(file, addCards);
                return true;
            }
        }.execute();
    }

    private Component initializeDeleteButton() {
        JButton deleteButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/NewDraftDeckDialog/actions-edit-delete-icon.png"));
            deleteButton.setIcon(new ImageIcon(img));
            deleteButton.setMargin(new Insets(0, 0, 0, 0));
            deleteButton.setBorder(null);
            deleteButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteButton.addActionListener(e->
                new DeleteWorker(
                        brain,
                        this,
                        this::draftingBoxWasDiscarded
                ).execute());
        return deleteButton;
    }

    public void addFilterToFilterList(CardFilter newCardFilter) {
        filterCardsDialog.addFilterToFilterList(newCardFilter);
    }

    public void updateDraftDeckLog(String newLabel) {
        draftDeckLog.setText(String.format("%s\n%s", draftDeckLog.getText(), newLabel));
        repaint();
    }

    public void tidyUp() {
        draftDeckLog.setText("Created empty draft deck.");
        //draftDeckSizeLabel.setText("Draft deck is empty.");
    }

    public void updateDraftDeckSize(int newSize) {
        //draftDeckSizeLabel.setText(String.format("Draft deck contains %d cards.", newSize));
    }

    public void updateCurrentCardsFiltered(int cardsFilteredByFilterListSize) {
        filterCardsDialog.updateCurrentCardsFiltered(cardsFilteredByFilterListSize);
    }

    public void draftingBoxWasDiscarded() {
        draftDeckLog.setText(String.format("%s\n%s\n%s", draftDeckLog.getText(), "Discarded all cards.","Created new empty draft deck."));
        //draftDeckSizeLabel.setText("Draft deck is empty.");
    }

    public void updateFilterListFromBrain() {
        filterCardsDialog.updateFilterListFromBrain();
    }
}
