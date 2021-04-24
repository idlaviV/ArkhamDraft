package arkhamDraft.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutionException;

public class ImageCrawler extends SwingWorker<Boolean, Void> {
    private final String id;
    private Image im;
    private final JLabel previewLabel;

    public ImageCrawler(String id, JLabel previewLabel) {
        this.id = id;
        this.previewLabel = previewLabel;

    }

    public Image getCard() {
        String dir = "./data/cards";
        File directory = new File(dir);
        if (!directory.exists())
        {
            directory.mkdir();
        }

        String fileName = String.format("%s.jpg", id);
        Path filePath = Paths.get(String.format("%s/%s", dir, fileName));
        String urlPath1 = String.format("https://arkhamdb.com/bundles/cards/%s", fileName);
        String urlPath2 = String.format("https://arkhamdb.com/bundles/cards/%s.png", id);
        File file = filePath.toFile();

        if (id.length() != 5 || !id.matches("[0-9]+")) {
            throw new RuntimeException(String.format("Tried to load image with illegal id %s", id));
        }

        if (!file.exists()) {
            try {
                extractURLtoFile(filePath, urlPath1, file);
            } catch (IOException e) {
                try {
                    extractURLtoFile(filePath, urlPath2, file);
                } catch (IOException e2) {
                    try {
                        return ImageIO.read(getClass().getResource("/cards/00000.jpg"));
                    } catch (IOException e3) {
                        throw new RuntimeException(e3);
                    }
                }
            }
        }
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void extractURLtoFile(Path filePath, String urlPath1, File file) throws IOException {
                InputStream in = new URL(urlPath1).openStream();
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
    }


    @Override
    protected Boolean doInBackground() throws Exception {

        im = getCard();
        return true;
    }

    @Override
    protected void done() {
        boolean status;
        try {
            // Retrieve the return value of doInBackground.
            status = get();
            if (status) {
                update();
            }
        } catch (InterruptedException e) {
            // This is thrown if the thread's interrupted.
            e.printStackTrace();
        } catch (ExecutionException e) {
            // This is thrown if we throw an exception
            // from doInBackground.
            e.printStackTrace();
        }
    }

    protected void update() throws InterruptedException {
        for (int waited = 0; waited<10; waited++) {
            if (im == null) {
                this.wait(100);
            } else {
                waited = 100;
                previewLabel.setIcon(new ImageIcon(im));
            }
        }
    }
}
