import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArkhamDraftStarter {
    private static final Path LOCAL_ARKHAM_DRAFT_PATH = Paths.get("ArkhamDraft.jar");
    public static final String URL_GITHUB_ARKHAM_DRAFT = "https://github.com/idlaviV/ArkhamDraft/raw/master/ArkhamDraft.jar";

    public void run() {
        File arkhamDraftJar = LOCAL_ARKHAM_DRAFT_PATH.toFile();
        if (arkhamDraftJar.exists()) {
            if (checkForUpdate()) {
                if (askForUpdate()) {
                    downloadJar();
                }
            }
        } else {
            if (askForDownload()) {
                downloadJar();
            } else {
                return;
            }
        }
        tryToRun();
    }

    private void tryToRun() {
        try {
            Starter.startArkhamDraft();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void downloadJar() {
        try {
            URL arkhamDraftRemote = new URL(URL_GITHUB_ARKHAM_DRAFT);
            ReadableByteChannel readableByteChannel = Channels.newChannel(arkhamDraftRemote.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(LOCAL_ARKHAM_DRAFT_PATH.toFile());
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new RuntimeException("Download of jar did not succeed.", e);
        }
    }

    private boolean askForUpdate() {
        return (JOptionPane.showConfirmDialog(
                null,
                "There is a new version of ArkhamDraft available. Do you want to download now?",
                "Update",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null
        ) == 0);
    }

    private boolean checkForUpdate() {
        GithubUpdater updater = new GithubUpdater("ArkhamDraft.jar","idlaviV/ArkhamDraft");
        try {
            String localHash = updater.getLocalHash();
            String remoteHash = updater.getRemoteHash();
            return !localHash.equals(remoteHash);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,
                "Tried to check whether local files are up-to-date, but failed.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    private boolean askForDownload() {
        return (JOptionPane.showConfirmDialog(
                null,
                "The starter could not find ArkhamDraft. Do you want to download it now?",
                "Not Found",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null
        ) == 0);
    }
}
