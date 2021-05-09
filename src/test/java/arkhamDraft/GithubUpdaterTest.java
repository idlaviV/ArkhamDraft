package arkhamDraft;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class GithubUpdaterTest {

    public static final String SHA = "ea11b610b5336a5c91afc26049c5245243041d23";
    GithubUpdater updater;

    @Before
    public void setup() throws IOException {
        updater = new GithubUpdater("ArkhamDraft.jar?ref=maven","idlaviV/ArkhamDraft");
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
