
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GithubUpdater {
    private final String elementPath;
    private final String githubRepoIdentifier;
    private final String localPath;

    enum ParserState {
        BEFORE_SHA,
        IN_SHA
    }

    public GithubUpdater(String elementPath, String githubRepoIdentifier, String localPath) {
        this.localPath = localPath;
        this.elementPath = elementPath;
        this.githubRepoIdentifier = githubRepoIdentifier;
    }

    public String getRemoteHash() throws IOException {
        String urlString =
                "https://api.github.com/repos/" + githubRepoIdentifier + "/contents/" + elementPath;
        String necessaryResponse;

        try (InputStream inputStream = new URL(urlString).openStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {

            ParserState state = ParserState.BEFORE_SHA;
            StringBuilder responseSoFar = new StringBuilder();
            char[] buffer = new char[50];
            while ((reader.read(buffer)) > 0) {
                responseSoFar.append(buffer);

                if (state == ParserState.IN_SHA) {
                    break;
                }

                if (responseSoFar.toString().contains("\"sha\"")) {
                    state = ParserState.IN_SHA;
                }
            }
            necessaryResponse = responseSoFar.toString();
        }

        return readSHAFromFile(necessaryResponse);
    }

    public String getLocalHash() throws IOException {
        Path localPath = Paths.get(this.localPath);
        try {
            return getGitHash(Files.readAllBytes(localPath));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String getGitHash(byte[] data) throws IOException, NoSuchAlgorithmException {
        ByteArrayOutputStream fullData = new ByteArrayOutputStream();
        fullData.write("blob ".getBytes(StandardCharsets.US_ASCII));
        fullData.write(Integer.toString(data.length).getBytes(StandardCharsets.US_ASCII));
        fullData.write(0);
        fullData.write(data);

        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] digested = digest.digest(fullData.toByteArray());
        StringBuilder output = new StringBuilder();
        for (byte b : digested) {
            StringBuilder newInt = new StringBuilder(Integer.toHexString(b & 0xFF));
            while (newInt.length()<2) {
                newInt.insert(0, "0");
            }
            output.append(newInt);
        }
        return output.toString();

    }

    private String readSHAFromFile(String necessaryResponse) {
        Pattern pattern = Pattern.compile("\"sha\":\\s*\"(.+?)\"");
        Matcher matcher = pattern.matcher(necessaryResponse);
        if (!matcher.find()) {
            return "N/A";
        }
        return matcher.group(1);
    }
}
