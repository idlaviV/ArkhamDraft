package arkhamDraft.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageCrawler {
    public Image getCard(String id) {
        String fileName = String.format("%s.jpg", id);
        Path filePath = Paths.get(String.format("src/main/resources/cards/%s", fileName));
        String urlPath = String.format("https://arkhamdb.com/bundles/cards/%s", fileName);
        File file = filePath.toFile();
        try {
            if (!file.exists()) {
                if (id.length() == 5 && id.matches("[0-9]+")) {
                    InputStream in = new URL(urlPath).openStream();
                    Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new RuntimeException(String.format("Tried to load image with illegal id %s", id));
                }
            }
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean imageIsCached(String fileName) {
        File file = Paths.get(String.format("src/main/resources/cards/%s", fileName)).toFile();
        return file.exists();
    }

}
