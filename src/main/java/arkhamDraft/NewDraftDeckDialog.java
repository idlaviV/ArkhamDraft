package arkhamDraft;

import arkhamDraft.workerPool.DeleteButtonWorker;
import arkhamDraft.workerPool.FinalizeDraftDeckButtonWorker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.lang.Thread.sleep;

public class NewDraftDeckDialog extends JDialog {
    private final Brain brain;
    private JTextArea draftDeckLog;
    private JLabel draftDeckSizeLabel;
    private FilterCardsDialog filterCardsDialog;
    private final JFileChooser fc = new JFileChooser();
    private final Consumer<Deck> printCardsToDraftPanel;
    private Runnable enableDraft;

    public NewDraftDeckDialog(Brain brain, Consumer<Deck> printCardsToDraftPanel, Runnable enableDraft) {
        super();
        this.brain = brain;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
        this.enableDraft = enableDraft;
        initializeDialogue();
        //setVisible(true);
    }

    private void initializeDialogue() {
        setTitle("New Draft Deck");
        setSize(300,300);
        setLocation(100,100);
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel currentDraftDeckPanel = new JPanel();
        currentDraftDeckPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(currentDraftDeckPanel);

        draftDeckSizeLabel = new JLabel("Draft deck is empty");
        currentDraftDeckPanel.add(draftDeckSizeLabel);

        draftDeckLog = new JTextArea("Created empty draft deck.", 5, 20);
        JScrollPane draftDeckLogScrollPane = new JScrollPane(draftDeckLog);
        draftDeckLogScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        currentDraftDeckPanel.add(draftDeckLogScrollPane);
        draftDeckLog.setBackground(this.getBackground());

        JButton addCardsButton = new JButton("Add Cards");
        add(addCardsButton);
        filterCardsDialog = new FilterCardsDialog(brain, this::addCards);
        addCardsButton.addActionListener(e -> {
            filterCardsDialog.tidyUp();
            brain.guiEntersFilterCardsDialog();
            filterCardsDialog.setVisible(true);
        });


        add(initializeFinalizeDraftDeckButton());
        add(initializeSaveButton());
        add(initializeLoadButton());
        add(initializeDeleteButton());
    }

    private SwingWorker<Integer, Void> addCards(Boolean dummy) {
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
                    new FinalizeDraftDeckButtonWorker(
                            brain,
                            this::dispose,
                            printCardsToDraftPanel,
                            enableDraft
                    ).execute();
                });
        return finalizeDraftDeckButton;
    }

    private Component initializeSaveButton() {
        JButton saveButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/actions-document-save-icon.png"));
            saveButton.setIcon(new ImageIcon(img));
            saveButton.setMargin(new Insets(0, 0, 0, 0));
            saveButton.setBorder(null);
            saveButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveButton.addActionListener(e->{
            File directory = new File("cardFilter");
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
            Image img = ImageIO.read(getClass().getResource("/icons/actions-document-open-folder-icon.png"));
            loadButton.setIcon(new ImageIcon(img));
            loadButton.setMargin(new Insets(0, 0, 0, 0));
            loadButton.setBorder(null);
            loadButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadButton.addActionListener(e -> {
            File directory = new File("cardFilter");
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
        Function<Boolean, SwingWorker<Integer, Void>> addCards = this::addCards;
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
            Image img = ImageIO.read(getClass().getResource("/icons/actions-edit-delete-icon.png"));
            deleteButton.setIcon(new ImageIcon(img));
            deleteButton.setMargin(new Insets(0, 0, 0, 0));
            deleteButton.setBorder(null);
            deleteButton.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteButton.addActionListener(e->
                new DeleteButtonWorker(
                        brain,
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
        draftDeckSizeLabel.setText("Draft deck is empty.");
    }

    public void updateDraftDeckSize(int newSize) {
        draftDeckSizeLabel.setText(String.format("Draft deck contains %d cards.", newSize));
    }

    public void updateCurrentCardsFiltered(int cardsFilteredByFilterListSize) {
        filterCardsDialog.updateCurrentCardsFiltered(cardsFilteredByFilterListSize);
    }

    public void draftingBoxWasDiscarded() {
        draftDeckLog.setText(String.format("%s\n%s\n%s", draftDeckLog.getText(), "Discarded all cards.","Created new empty draft deck."));
        draftDeckSizeLabel.setText("Draft deck is empty.");
    }

    public void updateFilterListFromBrain() {
        filterCardsDialog.updateFilterListFromBrain();
    }
}
