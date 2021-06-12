import java.io.IOException;

public class Starter {
    public static void startArkhamDraft() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "ArkhamDraft.jar");

        //For Debugging
        /*
        java.io.File output = new java.io.File("output.txt");
        output.delete();
        output.createNewFile();
        pb.redirectOutput(output);
        pb.redirectError(output);
        */
        Process p = pb.start();
        int status = p.waitFor();
        System.out.println("Exited with status: " + status);
    }
}
