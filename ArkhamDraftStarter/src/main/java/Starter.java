import java.io.File;
import java.io.IOException;

public class Starter {
    public static void startArkhamDraft() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "ArkhamDraft.jar");

        File output = new File("output.txt");
        output.delete();
        output.createNewFile();
        pb.redirectOutput(output);
        pb.redirectError(output);
        Process p = pb.start();
        int status = p.waitFor();
        System.out.println("Exited with status: " + status);
    }
}
