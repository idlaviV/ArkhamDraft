import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class GithubUpdaterTest {

    public static final String SHA = "cd7cedba48c887708aed34e04972304a8de09b1b";//If tests dont work, first update this to the sha of https://api.github.com/repos/idlaviV/ArkhamDraft/contentsArkhamDraft.jar.
    GithubUpdater updater;

    @Before
    public void setup() {
        updater = new GithubUpdater("ArkhamDraft.jar","idlaviV/ArkhamDraft", "./ArkhamDraft.jar");
    }

    @Test
    public void getRemoteHash() throws IOException {
        String value = updater.getRemoteHash();
        Assert.assertEquals(SHA, value);
    }

    @Test
    public void getLocalHash() throws IOException {
        Assert.assertEquals(SHA, updater.getLocalHash());
    }

    @Test
    public void getGitHash() throws Exception {
        Assert.assertEquals(SHA,
                updater.getGitHash(Files.readAllBytes(Paths.get("./ArkhamDraft.jar"))));
    }
}
