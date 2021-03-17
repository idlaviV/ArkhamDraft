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

public class ImageCrawler extends SwingWorker<Image, Image> {
    private final String id;
    private Image im;
    private final JLabel previewLabel;

    public ImageCrawler(String id, JLabel previewLabel) {
        this.id = id;
        this.previewLabel = previewLabel;

    }

    public Image getCard() {
        String fileName = String.format("%s.jpg", id);
        Path filePath = Paths.get(String.format("src/main/resources/cards/%s", fileName));
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
                    e.printStackTrace();
                    e2.printStackTrace();
                }
            }
        }
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void extractURLtoFile(Path filePath, String urlPath1, File file) throws IOException {
                InputStream in = new URL(urlPath1).openStream();
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
    }


    @Override
    protected Image doInBackground() throws Exception {
        im = getCard();
        return im;
    }

    @Override
    protected void done() {
        previewLabel.setIcon(new ImageIcon(im));
    }
}
